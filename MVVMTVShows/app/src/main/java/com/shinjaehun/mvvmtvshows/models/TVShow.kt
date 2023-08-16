package com.shinjaehun.mvvmtvshows.models

import com.google.gson.annotations.SerializedName

data class TVShow (
    val id: Int,
    val name: String,
    val startDate: String,
    val country: String,
    val network: String,
    val status: String,
    val image_thumbnail_path: String
)