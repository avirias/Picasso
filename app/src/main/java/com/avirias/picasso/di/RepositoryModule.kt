package com.avirias.picasso.di

import com.avirias.picasso.data.network.abstraction.PhotoWebService
import com.avirias.picasso.data.network.util.PhotoMapper
import com.avirias.picasso.data.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesPhotoRepository(
        photoWebService: PhotoWebService,
        photoMapper: PhotoMapper
    ) = PhotoRepository(photoWebService,photoMapper)
}