package com.shinjaehun.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shinjaehun.notesapp.dao.NoteDao
import com.shinjaehun.notesapp.entities.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "tv_shows_db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}