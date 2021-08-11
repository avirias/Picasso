package com.avirias.picasso.data.network.abstraction

import com.avirias.picasso.data.network.model.response.PhotoResponse
import com.avirias.picasso.domain.Resource

interface PhotoWebService {
    suspend fun getPhotos(): Resource<List<PhotoResponse>>
}