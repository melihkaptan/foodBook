package com.melhkptn.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.melhkptn.besinlerkitabi.R


fun ImageView.downloadImage(url: String?, context: Context) {

    val options =
        RequestOptions().placeholder(createPlaceHolder(context)).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImageBinding")
fun downloadImageBinding(view: ImageView, url: String?) {
    view.downloadImage(url, view.context)
}


