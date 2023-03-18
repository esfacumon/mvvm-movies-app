package com.example.wembleymovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymovies.data.api.ApiConstants
import com.example.wembleymovies.data.api.ApiModule
import com.example.wembleymovies.data.api.ApiService
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.model.MovieAdapter
import com.example.wembleymovies.data.model.MovieResponse
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
        return inflater.inflate(R.layout.fragment_search_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view_search)
        loadPopularMovies(view)
    }

    private fun loadPopularMovies(view: View) {
        apiService.getPopularMovies(ApiConstants.API_KEY).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse: MovieResponse? = response.body()
                    movieResponse?.movies?.let { movies.addAll(it) } // adds all movies got from requests to movies if it's not null
                    val btnFav = view.findViewById<ImageButton>(R.id.image_btn_fav)

                    btnFav.setOnClickListener{

                    }
                    movieAdapter = MovieAdapter(movies)
                    recyclerView.adapter = movieAdapter
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API", "Error on api request: ${t.localizedMessage}")
            }
        })
    }

    private fun isFav(movie: Movie): Boolean {
        val favMovies: MutableSet<String>? = loadFavMovies()
        if (favMovies != null) {
            for (favMovie in favMovies) {
                if (movie.id == favMovie.toInt()) {
                    return true
                }
            }
        }
        return false
    }

    private fun loadFavMovies(): MutableSet<String>? {
        // val sp = PreferenceManager.getDefaultSharedPreferences(this)
        // return sp.getStringSet("favSatellites", null)
    }

    private fun getPopularMovies() {
        apiService.getPopularMovies(ApiConstants.API_KEY).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieReponse: MovieResponse? = response.body()
                    val movies: List<Movie>? = response.body()?.movies
                    Log.d("API_TEST", "Encontradas ${movieReponse?.movies?.size} peliculas populares: $movies")
                    Log.d("MOVIE_RESPONSE", "num de paginas: ${movieReponse?.totalPages}")

                } else {
                    Log.e("API_TEST", "Error al obtener películas populares: ${response.code()}")
                }
            }


            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API_TEST", "Error al llamar a la API: ${t.localizedMessage}")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchMoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchMoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}