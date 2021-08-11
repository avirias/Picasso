package com.avirias.picasso.data.network.service

import com.avirias.picasso.data.network.model.response.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET

interface PhotoService {

    @GET("b/VGOL")
    suspend fun getPhotos(): Response<List<PhotoResponse>>
}