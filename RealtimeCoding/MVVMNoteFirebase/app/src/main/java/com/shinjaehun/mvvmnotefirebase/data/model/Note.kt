package com.shinjaehun.mvvmnotefirebase.data.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.Date

//data class Note (
//    var id: String = "",
//    val text: String = "",
//
//    @ServerTimestamp
//    val date: Date = Date()
//)

@Parcelize
data class Note (
    var id: String = "",
    var user_id: String = "",
    val title: String = "",
    val description: String = "",
    val tags: MutableList<String> = arrayListOf(),
    val images: List<String> = arrayListOf(),
    @ServerTimestamp
    val date: Date = Date(),
): Parcelable
