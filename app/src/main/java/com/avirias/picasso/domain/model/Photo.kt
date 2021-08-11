package com.avirias.picasso.domain.model

import com.avirias.picasso.util.extensions.DateSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable(DateSerializer::class)
data class Photo(
    val id: String,
    val title: String,
    val publishedAt: Date,
    val comment: String,
    val picture: String
): java.io.Serializable