package com.example.wembleymovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.example.wembleymovies.data.api.ApiConstants
import com.example.wembleymovies.data.api.ApiModule
import com.example.wembleymovies.data.api.ApiService
import com.example.wembleymovies.data.managers.FavoritesManager
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.model.MovieAdapter
import com.example.wembleymovies.data.model.MovieRepository
import com.example.wembleymovies.data.model.MovieResponse
import com.example.wembleymovies.data.viewmodel.SearchMoviesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchMoviesFragment : Fragment(R.layout.fragment_search_movies) {

    private val apiService: ApiService by lazy { ApiModule.provideApiService() } // lazy init for optimization
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private var movies: MutableList<Movie> = mutableListOf()
    private lateinit var searchMoviesViewModel: SearchMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieRepository = MovieRepository(apiService)

        searchMoviesViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchMoviesViewModel(movieRepository) as T
            }
        }).get(SearchMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_search)
        loadPopularMovies(view)

        val searchView = view.findViewById<SearchView>(R.id.search_bar)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    apiService.searchMovies(ApiConstants.API_KEY, query).enqueue(object : Callback<MovieResponse> {
                        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                            if (response.isSuccessful) {
                                val movieResponse: MovieResponse? = response.body()
                                movies.clear()
                                // movieResponse?.movies?.let { movies.addAll(it) } // adds all movies got from requests to movies if it's not null
                                searchMoviesViewModel.searchedMovies.clear()
                                movieResponse?.movies?.let { searchMoviesViewModel.searchedMovies.addAll(it) }
                                setupMovieAdapter()
                            }
                        }

                        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                            Log.e("API", "Error on api request: ${t.localizedMessage}")
                        }
                    })
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
    }

    private fun loadPopularMovies(view: View) {
        apiService.getPopularMovies(ApiConstants.API_KEY).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse: MovieResponse? = response.body()
                    movies.clear()
                    movieResponse?.movies?.let { movies.addAll(it) } // adds all movies got from requests to movies if it's not null
                    setupMovieAdapter()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API", "Error on api request: ${t.localizedMessage}")
            }
        })
    }


    private fun setupMovieAdapter() {
        var moviesToShow: List<Movie> = mutableListOf()
        val favoritesManager = FavoritesManager(requireContext())

        if (searchMoviesViewModel.searchedMovies.isEmpty()) moviesToShow = movies else moviesToShow = searchMoviesViewModel.searchedMovies

        movieAdapter = MovieAdapter(moviesToShow, favoritesManager, object : MovieAdapter.OnFavoriteClickListener {
            override fun onFavoriteClick(movie: Movie, isFavorite: Boolean) { /* empty function in this case */}
        })
        recyclerView.adapter = movieAdapter
    }
}