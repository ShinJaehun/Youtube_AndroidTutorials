package com.shinjaehun.mvvmnotefirebase.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var user_id: String = "",
    var description: String = "",
    val date: String = ""
): Parcelable