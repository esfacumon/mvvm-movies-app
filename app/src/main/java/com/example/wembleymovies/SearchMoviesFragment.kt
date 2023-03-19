package com.example.wembleymovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.example.wembleymovies.data.api.ApiModule
import com.example.wembleymovies.data.api.ApiService
import com.example.wembleymovies.data.managers.FavoritesManager
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.model.MovieAdapter
import com.example.wembleymovies.data.model.MovieRepository
import com.example.wembleymovies.data.viewmodel.SearchMoviesViewModel



class SearchMoviesFragment : Fragment(R.layout.fragment_search_movies) {

    private val apiService: ApiService by lazy { ApiModule.provideApiService() } // lazy init for optimization
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchMoviesViewModel: SearchMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieRepository = MovieRepository(apiService)

        searchMoviesViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchMoviesViewModel(movieRepository) as T
            }
        }).get(SearchMoviesViewModel::class.java)

        recyclerView = view.findViewById(R.id.recycler_view_search)

        searchMoviesViewModel.movies.observe(viewLifecycleOwner) {movies ->
            setupMovieAdapter(movies)
        }

        if (!searchMoviesViewModel.calledSearchMovies) {
            searchMoviesViewModel.getPopularMovies()
        }

        val searchView = view.findViewById<SearchView>(R.id.search_bar)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchMoviesViewModel.searchMovies(query)
                }
                if (query.equals("")) {
                    searchMoviesViewModel.getPopularMovies()
                }
                return true
            }
        })
    }


    private fun setupMovieAdapter(movies: List<Movie>) {
        val favoritesManager = FavoritesManager(requireContext())
        movieAdapter = MovieAdapter(movies, favoritesManager, object : MovieAdapter.OnFavoriteClickListener {
            override fun onFavoriteClick(movie: Movie, isFavorite: Boolean) { /* empty function in this case */}
        })
        recyclerView.adapter = movieAdapter
    }
}