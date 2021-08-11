package com.avirias.picasso.di

import com.avirias.picasso.BuildConfig
import com.avirias.picasso.data.network.abstraction.PhotoWebService
import com.avirias.picasso.data.network.implementation.PhotoWebServiceImpl
import com.avirias.picasso.data.network.service.PhotoService
import com.avirias.picasso.data.network.util.PhotoMapper
import com.avirias.picasso.domain.constants.Network
import com.avirias.picasso.domain.util.DateConvertor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor())
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(Network.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providesPhotoService(
        retrofit: Retrofit
    ) = retrofit.create(PhotoService::class.java)

    @Provides
    @Singleton
    fun providesPhotoWebService(
        photoService: PhotoService
    ): PhotoWebService = PhotoWebServiceImpl(photoService)

    @Provides
    @Singleton
    fun providesDateFormatter(): SimpleDateFormat {
        return SimpleDateFormat("", Locale.ROOT)
    }

    @Provides
    @Singleton
    fun providesDateConvertor(
        simpleDateFormat: SimpleDateFormat
    ) = DateConvertor(simpleDateFormat)

    @Provides
    @Singleton
    fun providesPhotoMapper(
        dateConvertor: DateConvertor
    ) = PhotoMapper(dateConvertor)
}