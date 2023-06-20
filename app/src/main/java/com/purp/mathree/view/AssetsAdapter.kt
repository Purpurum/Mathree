package com.purp.mathree.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.IOException

@BindingAdapter("assetImage")
fun loadImageFromAsset(imageView: ImageView, assetPath: String) {
    val context = imageView.context
    val drawable: Drawable? = Drawable.createFromStream(context.assets.open(assetPath), null)
    Glide.with(context)
        .load(drawable)
        .into(imageView)
}

@BindingAdapter("app:imageResource")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
