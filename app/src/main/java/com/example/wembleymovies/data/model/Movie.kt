package com.example.wembleymovies.data.model

import com.google.gson.annotations.SerializedName

/**
 * Represents movie data we want to display in our list
 */
data class Movie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("poster_path")
    val posterPath: String? // could be null
)