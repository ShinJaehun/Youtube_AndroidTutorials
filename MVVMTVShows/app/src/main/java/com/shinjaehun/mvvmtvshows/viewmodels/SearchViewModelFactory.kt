package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shinjaehun.mvvmtvshows.repositories.MostPopularTVShowsRepository
import com.shinjaehun.mvvmtvshows.repositories.SearchTVShowRepository

class SearchViewModelFactory(
    val app: Application,
    val repository: SearchTVShowRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
        return SearchViewModel(app, repository) as T
    }

}