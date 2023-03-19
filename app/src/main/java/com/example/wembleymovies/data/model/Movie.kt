package com.example.wembleymovies.data.model

import com.google.gson.annotations.SerializedName

/**
 * Represents movie data we want to use
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

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String?, // could be null

    var timestamp: Long? = null
)
