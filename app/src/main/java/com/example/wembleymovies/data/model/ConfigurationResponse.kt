package com.example.wembleymovies.data.model

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("base_url")
    val baseUrl: String,

    @SerializedName("poster_sizes")
    val posterSizes: List<String>
)
