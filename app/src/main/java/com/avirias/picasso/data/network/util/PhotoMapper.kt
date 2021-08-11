package com.avirias.picasso.data.network.util

import com.avirias.picasso.data.network.model.response.PhotoResponse
import com.avirias.picasso.domain.model.Photo
import com.avirias.picasso.domain.util.DateConvertor
import com.avirias.picasso.domain.util.EntityMapper
import javax.inject.Inject

class PhotoMapper @Inject constructor(
    private val dateConvertor: DateConvertor
) : EntityMapper<PhotoResponse, Photo> {

    override fun mapFromEntity(entity: PhotoResponse) = Photo(
        id = entity.id,
        title = entity.title,
        publishedAt = dateConvertor.convertStringToDate(entity.publishedAt),
        comment = entity.comment,
        picture = entity.picture
    )

    // This function will never used
    override fun mapToEntity(domainModel: Photo): PhotoResponse {
        throw NotImplementedError()
    }
}