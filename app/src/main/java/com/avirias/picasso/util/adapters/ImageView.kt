package com.avirias.picasso.util.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.avirias.picasso.R
import com.avirias.picasso.domain.model.Photo

@BindingAdapter("load")
fun ImageView.loadImage(photo: Photo) {
    this.load(photo.picture) {
        crossfade(true)
        placeholder(R.drawable.placeholder)
        memoryCacheKey(photo.id)
    }
}