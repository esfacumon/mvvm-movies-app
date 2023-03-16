package com.example.wembleymovies.data.model

import com.google.gson.annotations.SerializedName


/**
 * Represents api response data
 */
data class MovieResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)
