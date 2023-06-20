package com.purp.mathree.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter("app:srcCompatLiveData")
fun setSrcCompatLiveData(imageView: ImageView, drawable: LiveData<Int>?) {
    drawable?.value?.let {
        imageView.setImageResource(it)
    }
}