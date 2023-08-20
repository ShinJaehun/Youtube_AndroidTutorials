package com.shinjaehun.mvvmtvshows.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shinjaehun.mvvmtvshows.dao.TVShowDao
import com.shinjaehun.mvvmtvshows.models.TVShow

@Database(
    entities = [TVShow::class],
    version = 1
)
abstract class TVShowsDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TVShowDao

    companion object {

        @Volatile
        private var instance: TVShowsDatabase? = null

        fun getTVShowsDatabase(context: Context) : TVShowsDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TVShowsDatabase::class.java,
                    "tv_shows_db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

    }
}