package com.example.wembleymovies.data.model

import android.util.Log
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.api.ApiConstants
import com.example.wembleymovies.data.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val apiService: ApiService) {
//    // cambiar por la funcion que uso en el mainactivity
//    fun searchMovies(query: String): List<Movie> {
//        apiService.searchMovies(ApiConstants.API_KEY, "Shrek").enqueue(object :
//            Callback<MovieResponse> {
//            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
//                if (response.isSuccessful) {
//                    val movieReponse: MovieResponse? = response.body()
//                    val movies = response.body()?.movies
//                    Log.d("API_TEST", "Encontradas ${movieReponse?.totalResults} peliculas:  $movies")
//                } else {
//                    Log.e("API_TEST", "Error al obtener películas populares: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
//                Log.e("API_TEST", "Error al llamar a la API: ${t.localizedMessage}")
//            }
//        })
//    }
//
//    suspend fun getPopularMovies(): List<Movie>{
//        val response = apiService.getPopularMovies(ApiConstants.API_KEY)
//        if (response.isSuccessful) {
//            return response.body()?.movies ?: emptyList()
//        } else {
//            throw Exception("Error fetching movies: ${response.errorBody()?.string()}")
//        }
//    }
}