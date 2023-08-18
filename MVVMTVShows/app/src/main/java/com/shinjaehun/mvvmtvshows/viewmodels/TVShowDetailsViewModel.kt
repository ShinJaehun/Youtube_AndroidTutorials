package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.mvvmtvshows.MVVMTVShowsApplication
import com.shinjaehun.mvvmtvshows.repositories.TVShowDetailsRepository
import com.shinjaehun.mvvmtvshows.responses.TVShowDetailsResponse
import kotlinx.coroutines.launch

class TVShowDetailsViewModel(
    app: Application,
    private val tvShowDetailsRepository: TVShowDetailsRepository
) : AndroidViewModel(app) {

    var tvShowDetails: LiveData<TVShowDetailsResponse> = MutableLiveData()

    fun getTVShowDetails(tvShowId: String) = viewModelScope.launch {
        tvShowDetails = tvShowDetailsRepository.getTVShowDetails(tvShowId)

    }

    private fun hasInternetConnection(): Boolean {
        // 자동으로 뭔가 이렇게 줄여놨음!!
        val connectivityManager = getApplication<MVVMTVShowsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}