package com.avirias.picasso.util.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("url")
fun ImageView.loadImage(url: String) {
    this.load(url)
}