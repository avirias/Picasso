package com.avirias.picasso.data.network.model.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val publishedAt: String,
    val comment: String,
    val picture: String
)
