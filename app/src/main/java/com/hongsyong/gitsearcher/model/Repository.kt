package com.hongsyong.gitsearcher.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("score") val score: Double
)