package com.mobile.app.themovies.util

import android.widget.ImageView
import com.mobile.app.themovies.BuildConfig
import com.squareup.picasso.Picasso

fun ImageView.fromUrl(url: String, size: String= "w500") {
    Picasso.with(context)
            .load("${BuildConfig.IMAGES_BASE_URL}$size/$url")
            .into(this)
}