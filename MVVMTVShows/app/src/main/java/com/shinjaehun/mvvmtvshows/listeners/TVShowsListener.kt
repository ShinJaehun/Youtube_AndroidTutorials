package com.shinjaehun.mvvmtvshows.listeners

import com.shinjaehun.mvvmtvshows.models.TVShow

interface TVShowsListener {
    fun onTVShowClick(tvShow: TVShow)
}