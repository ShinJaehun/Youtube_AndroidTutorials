package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WatchListViewModelFactory(
    val app: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WatchListViewModel(app) as T
    }
}