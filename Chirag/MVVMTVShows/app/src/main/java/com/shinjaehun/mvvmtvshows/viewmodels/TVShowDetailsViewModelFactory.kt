package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shinjaehun.mvvmtvshows.repositories.TVShowDetailsRepository

class TVShowDetailsViewModelFactory(
    val app: Application,
    val repository: TVShowDetailsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TVShowDetailsViewModel(app, repository) as T
    }
}