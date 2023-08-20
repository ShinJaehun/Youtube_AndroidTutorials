package com.shinjaehun.mvvmtvshows.listeners

import com.shinjaehun.mvvmtvshows.models.TVShow

interface WatchListListener {
    fun onTVShowClicked(tvShow: TVShow)

    fun removeTVShowFromWatchList(tvShow: TVShow, position: Int)
}