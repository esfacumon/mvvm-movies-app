package com.example.wembleymovies.data.managers


import android.content.Context
import android.content.SharedPreferences
import com.example.wembleymovies.data.model.Movie
import com.google.gson.Gson

/**
 * Manages SharedPreferences to save favorite movies. Uses Gson to save all data class in JSON format string.
 */
class FavoritesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("favorite_movies", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveFavorite(movie: Movie) {
        movie.timestamp = System.currentTimeMillis()
        val movieJson = gson.toJson(movie)
        sharedPreferences.edit().putString(movie.id.toString(), movieJson).apply()
    }


    fun removeFavorite(movie: Movie) {
        sharedPreferences.edit().remove(movie.id.toString()).apply()
    }


    fun isFavorite(movie: Movie): Boolean {
        return sharedPreferences.contains(movie.id.toString())
    }


    fun getFavoriteMovies(): MutableList<Movie> {
        val favoriteMovies = mutableListOf<Movie>()
        sharedPreferences.all.forEach { (key, value) ->
            if (value is String) {
                val movie = gson.fromJson(value, Movie::class.java)
                favoriteMovies.add(movie)
            }
        }
        return favoriteMovies
    }
}