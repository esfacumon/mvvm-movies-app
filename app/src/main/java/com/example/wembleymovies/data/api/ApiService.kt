package com.example.wembleymovies.data.api

import com.example.wembleymovies.data.model.ConfigurationResponse
import com.example.wembleymovies.data.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines api requests used on the app.
 */
interface ApiService {
    /**
     * Gets list of popular movies.
     * @param apiKey
     * @return MovieResponse list of popular movies
     */
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MovieResponse>


    /**
     * Gets list of movies.
     * @param apiKey
     * @param query Search string.
     * @return List of movies which contains query string.
     */
    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponse>

    /**
     * Gets list of movies.
     * @param apiKey
     * @param query Search string.
     * @return List of movies which contains query string.
     */
    @GET("configuration")
    fun searchMovies(
        @Query("api_key") apiKey: String
    ): Call<ConfigurationResponse>
}