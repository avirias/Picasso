package com.avirias.picasso.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avirias.picasso.data.repository.PhotoRepository
import com.avirias.picasso.domain.Resource
import com.avirias.picasso.domain.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableSharedFlow<Resource<List<Photo>>>(1)
    val photos = _photos.asSharedFlow()

    val capturedImages = mutableListOf<Photo>()


    fun getPhotos() {

        val handler = CoroutineExceptionHandler { _, throwable ->
            _photos.tryEmit(Resource.Failure(throwable))
        }

        viewModelScope.launch(handler + CoroutineName("photoViewModel")) {
            _photos.emit(Resource.Loading())
            val response = photoRepository.getPhotos()
            _photos.emit(response)
        }
    }

    init {
        getPhotos()
    }
}