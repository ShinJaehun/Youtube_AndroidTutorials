package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shinjaehun.mvvmtvshows.repositories.MostPopularTVShowsRepository

class MostPopularTVShowsViewModelFactory(
    val app: Application,
    val repository: MostPopularTVShowsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
        return MostPopularTVShowsViewModel(app, repository) as T
    }

}