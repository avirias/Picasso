package com.avirias.picasso.data.network.implementation

import com.avirias.picasso.data.network.abstraction.DataSource
import com.avirias.picasso.data.network.abstraction.PhotoWebService
import com.avirias.picasso.data.network.model.response.PhotoResponse
import com.avirias.picasso.data.network.service.PhotoService
import com.avirias.picasso.domain.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoWebServiceImpl @Inject constructor(
    private val photoService: PhotoService
) : PhotoWebService, DataSource() {

    override suspend fun getPhotos(): Resource<List<PhotoResponse>> =
        result { photoService.getPhotos() }
}