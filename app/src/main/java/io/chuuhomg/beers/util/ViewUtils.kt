package io.chuuhomg.beers.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

@SuppressLint("CheckResult")
fun ImageView.loadUrl(url: String) {
    Glide.with(this.context).load(url).apply(RequestOptions().centerCrop()).into(this)
}