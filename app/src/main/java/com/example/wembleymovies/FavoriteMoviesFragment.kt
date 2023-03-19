package com.example.wembleymovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymovies.data.managers.FavoritesManager
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.model.MovieAdapter


class FavoriteMoviesFragment : Fragment(R.layout.fragment_search_movies), MovieAdapter.OnFavoriteClickListener {

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteMovies: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_favorites)
        loadFavoriteMovies()
    }


    /**
     * Load favorite movies saved on sharedPreferences and updates RecyclerView
     */
    private fun loadFavoriteMovies() {
        val favoritesManager = FavoritesManager(requireContext())
        favoriteMovies = favoritesManager.getFavoriteMovies().sortedBy { it.timestamp }.reversed().toMutableList()

        if (favoriteMovies.isEmpty()) {
            // handle error maybe show TextView "no movies marked as favourite"
            return
        }
        movieAdapter = MovieAdapter(favoriteMovies, favoritesManager, this)
        recyclerView.adapter = movieAdapter
    }


    override fun onFavoriteClick(movie: Movie, isFavorite: Boolean) {
        val pos: Int
        if (isFavorite) {
            favoriteMovies.add(movie)
            pos = favoriteMovies.indexOf(movie)
            movieAdapter.notifyItemInserted(pos)
        }
        else {
            pos = favoriteMovies.indexOf(movie)
            favoriteMovies.remove(movie)
            movieAdapter.notifyItemRemoved(pos)
        }
    }
}