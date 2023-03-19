package com.example.wembleymovies.data.model

import com.example.wembleymovies.data.api.ApiConstants
import com.example.wembleymovies.data.api.ApiService

class MovieRepository(private val apiService: ApiService) {

    suspend fun searchMovies(query: String): List<Movie> {
        val response = apiService.searchMovies(ApiConstants.API_KEY, query)
        if (response.isSuccessful) {
            return response.body()?.movies ?: emptyList()
        } else {
            throw Exception("Error fetching movies: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getPopularMovies(): List<Movie>{
        val response = apiService.getPopularMovies(ApiConstants.API_KEY)
        if (response.isSuccessful) {
            return response.body()?.movies ?: emptyList() // empty list IF movies is null
        } else {
            throw Exception("Error fetching movies: ${response.errorBody()?.string()}")
        }
    }
}