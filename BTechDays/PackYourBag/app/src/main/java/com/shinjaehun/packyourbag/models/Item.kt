package com.shinjaehun.packyourbag.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @ColumnInfo(name="itemname")
    val itemname: String,
    @ColumnInfo(name="category")
    val category: String,
    @ColumnInfo(name="addedby")
    val addedby: String,
    @ColumnInfo(name="checked")
    var checked: Boolean = false,
): java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}