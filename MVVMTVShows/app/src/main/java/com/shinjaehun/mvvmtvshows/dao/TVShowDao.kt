package com.shinjaehun.mvvmtvshows.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shinjaehun.mvvmtvshows.models.TVShow

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tvshows ")
    fun getWatchList() : LiveData<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(tvShow: TVShow)

    @Delete
    suspend fun removeFromWatchList(tvShow: TVShow)

    @Query("SELECT * FROM tvshows WHERE id = :tvShowId")
    fun getTVShowFromWatchList(tvShowId: String): LiveData<TVShow>

}