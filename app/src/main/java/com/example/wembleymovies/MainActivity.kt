package com.example.wembleymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import androidx.core.view.get

import com.example.wembleymovies.data.api.ApiConstants.API_KEY
import com.example.wembleymovies.data.api.ApiService
import com.example.wembleymovies.data.model.MovieResponse
import com.example.wembleymovies.data.api.ApiModule
import com.example.wembleymovies.data.model.Movie

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy { ApiModule.provideApiService() } // lazy init for optimization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController =  navHostFragment.navController

        val navController = this.findNavController(R.id.nav_host_fragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_menu)

        bottomNavigationView.setupWithNavController(navController)

        // findViewById<BottomNavigationView>(R.id.nav_menu).setupWithNavController(navController)

        apiPopularMovies()
        apiSearchMovies()
    }

    private fun apiPopularMovies() {
        apiService.getPopularMovies(API_KEY).enqueue(object : Callback<MovieResponse> {
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


    private fun apiSearchMovies() {
        apiService.searchMovies(API_KEY, "Shrek").enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieReponse: MovieResponse? = response.body()
                    val movies: List<Movie>? = response.body()?.movies
                    Log.d("API_TEST", "Encontradas ${movieReponse?.totalResults} peliculas:  $movies")
                } else {
                    Log.e("API_TEST", "Error al obtener películas populares: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("API_TEST", "Error al llamar a la API: ${t.localizedMessage}")
            }
        })
    }
}