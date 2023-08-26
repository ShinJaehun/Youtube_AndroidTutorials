package com.shinjaehun.notesapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date_time")
    val dateTime: String,

    @ColumnInfo(name = "subtitle")
    val subtitle: String,

    @ColumnInfo(name = "note_text")
    val noteText: String,

    @ColumnInfo(name = "image_path")
    val imagePath: String? = null,

    @ColumnInfo(name = "color")
    val color: String? = null,

    @ColumnInfo(name = "web_link")
    var webLink: String? = null
) : java.io.Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    override fun toString(): String {
        return "$title : $dateTime"
    }
}

// 나 미쳤나봐
//data class Note (
//    @ColumnInfo(name = "title")
//    private val title: String,
//
//    @ColumnInfo(name = "date_time")
//    private val dateTime: String,
//
//    @ColumnInfo(name = "subtitle")
//    private val subtitle: String,
//
//    @ColumnInfo(name = "note_text")
//    private val noteText: String,
//
//    @ColumnInfo(name = "image_path")
//    private val imagePath: String? = null,
//
//    @ColumnInfo(name = "color")
//    private val color: String? = null,
//
//    @ColumnInfo(name = "web_link")
//    private val webLink: String? = null
//) : java.io.Serializable {
//
//    @PrimaryKey(autoGenerate = true)
//    var id = 0
//
//    override fun toString(): String {
//        return "$title : $dateTime"
//    }
//}