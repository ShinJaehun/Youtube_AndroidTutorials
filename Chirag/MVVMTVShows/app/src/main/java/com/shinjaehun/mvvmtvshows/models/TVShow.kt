package com.shinjaehun.mvvmtvshows.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshows")
data class TVShow (
    @PrimaryKey
    val id: Int,
    val name: String,
    val startDate: String?,
    val country: String,
    val network: String,
    val status: String,
    val image_thumbnail_path: String
) : java.io.Serializable