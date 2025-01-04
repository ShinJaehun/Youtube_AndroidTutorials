package com.shinjaehun.packyourbag.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CheckListViewModelFactory(
    val app: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CheckListViewModel(app) as T
    }
}
