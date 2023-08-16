package com.shinjaehun.mvvmtvshows.responses

import com.shinjaehun.mvvmtvshows.models.TVShow

data class TVShowsResponse (
    val page: Int,
    val pages: Int,
    val tv_shows: List<TVShow>
)