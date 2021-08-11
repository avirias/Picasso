package com.avirias.picasso.domain.model

import java.util.*

data class Photo(
    val id: String,
    val title: String,
    val publishedAt: Date,
    val comment: String
)