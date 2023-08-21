package com.shinjaehun.notesapp.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    private val title: String,
    private val dateTime: String,
    private val subtitle: String,
    private val noteText: String,
    private val imagePath: String,
    private val color: String,
    private val webLink: String
) : java.io.Serializable {
    override fun toString(): String {
        return "$title : $dateTime"
    }
}