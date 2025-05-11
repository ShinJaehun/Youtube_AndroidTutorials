package com.shinjaehun.simpletodoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shinjaehun.simpletodoapp.Todo

@Database(entities = [Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    companion object {
        const val NANE = "Todo_DB"
    }

    abstract fun getTodoDao(): TodoDao
}