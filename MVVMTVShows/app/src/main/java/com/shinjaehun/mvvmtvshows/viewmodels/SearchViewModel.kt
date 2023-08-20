package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shinjaehun.mvvmtvshows.repositories.SearchTVShowRepository

class SearchViewModel(
    app: Application,
    private val searchTVShowRepository: SearchTVShowRepository
) : AndroidViewModel(app) {

    fun searchTVShow(query: String, page: Int) =
        searchTVShowRepository.searchTVShow(query, page)
}