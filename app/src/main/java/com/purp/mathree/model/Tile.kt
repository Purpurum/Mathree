package com.purp.mathree.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.purp.mathree.BR
import com.purp.mathree.R

data class Tile(
    var floorLevel: Int,
    var number: Int = 0,
    var drawable: Int = R.drawable.blank,
    var clicked: Boolean = false,
    var hasPlayer: Boolean = false
)
