package com.shinjaehun.imagefilterapp.listeners

import com.shinjaehun.imagefilterapp.data.ImageFilter

interface ImageFilterListener {
    fun onFilterSelected(imageFilter: ImageFilter)
}