package com.avirias.picasso.data.repository

import com.avirias.picasso.data.network.abstraction.PhotoWebService
import com.avirias.picasso.data.network.util.PhotoMapper
import com.avirias.picasso.domain.Resource
import com.avirias.picasso.domain.model.Photo
import dagger.hilt.android.scopes.ViewModelScoped
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class PhotoRepository @Inject constructor(
    private val photoWebService: PhotoWebService,
    private val photoMapper: PhotoMapper
) {

    suspend fun getPhotos(): Resource<List<Photo>> {
        return photoWebService.getPhotos().peek {
            Timber.d("network response is %s", this.toString())
        }.map {
            map { photoResponse ->
                photoMapper.mapFromEntity(photoResponse)
            }
        }
    }
}