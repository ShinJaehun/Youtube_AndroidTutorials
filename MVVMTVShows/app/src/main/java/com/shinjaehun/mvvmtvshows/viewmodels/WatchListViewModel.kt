package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.mvvmtvshows.database.TVShowsDatabase
import com.shinjaehun.mvvmtvshows.models.TVShow
import kotlinx.coroutines.launch

private const val TAG = "WatchListViewModel"

class WatchListViewModel(
    app: Application
) : AndroidViewModel(app) {

    private var tvShowsDatabase: TVShowsDatabase? = null

    init {
        if (tvShowsDatabase == null) {
            tvShowsDatabase = TVShowsDatabase.getTVShowsDatabase(app)
        }
    }

//    fun loadWatchList() : LiveData<List<TVShow>>? {
//        viewModelScope.launch {
//            tvShows = tvShowsDatabase?.tvShowDao()!!.getWatchList()
//            Log.i(TAG, "${tvShows!!.value!!.size}")
//        }
//        return tvShows
//    }

//    fun loadWatchList() =
//        viewModelScope.launch {
//            tvShows = tvShowsDatabase!!.tvShowDao()!!.getWatchList()
//            Log.i(TAG, "${tvShows!!.value!!.size}")
//        }

    fun loadWatchList() = tvShowsDatabase!!.tvShowDao().getWatchList()

    fun removeTVShowFromWatchList(tvShow: TVShow) {
        viewModelScope.launch {
            tvShowsDatabase!!.tvShowDao().removeFromWatchList(tvShow)
        }

    }

}

