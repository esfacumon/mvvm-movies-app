package com.example.wembleymovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymovies.data.managers.FavoritesManager
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.model.MovieAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteMoviesFragment : Fragment(R.layout.fragment_search_movies), MovieAdapter.OnFavoriteClickListener {


    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteMovies: MutableList<Movie>
    private var movies: MutableList<Movie> = mutableListOf()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        Log.d("favMovies", favoriteMovies.map { movie -> (movie.id.toString() + movie.title) }.toString())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteMoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteMoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onFavoriteClick(movie: Movie, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteMovies.add(movie)
        }
        else {
            favoriteMovies.remove(movie)
        }
        movieAdapter.notifyDataSetChanged()
    }
}