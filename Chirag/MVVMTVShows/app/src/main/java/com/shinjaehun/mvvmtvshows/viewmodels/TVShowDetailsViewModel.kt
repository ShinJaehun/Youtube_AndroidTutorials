package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.mvvmtvshows.MVVMTVShowsApplication
import com.shinjaehun.mvvmtvshows.database.TVShowsDatabase
import com.shinjaehun.mvvmtvshows.models.TVShow
import com.shinjaehun.mvvmtvshows.repositories.TVShowDetailsRepository
import com.shinjaehun.mvvmtvshows.responses.TVShowDetailsResponse
import kotlinx.coroutines.launch

private const val TAG = "TVShowDetailsViewModel"

class TVShowDetailsViewModel(
    app: Application,
    private val tvShowDetailsRepository: TVShowDetailsRepository
) : AndroidViewModel(app) {

    var tvShowDetails: LiveData<TVShowDetailsResponse> = MutableLiveData()

    private var tvShowsDatabase: TVShowsDatabase? = null

    init {
        tvShowsDatabase = TVShowsDatabase.getTVShowsDatabase(app)
    }

    fun getTVShowDetails(tvShowId: String) = viewModelScope.launch {
        if (hasInternetConnection()) {
            tvShowDetails = tvShowDetailsRepository.getTVShowDetails(tvShowId)
            Log.i(TAG, "tvShowDetails: ${tvShowDetails.value}")
        } else {
            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

//    fun getTVShowDetails(tvShowId: String) : LiveData<TVShowDetailsResponse> {
//        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
//    }

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

    fun addWatchList(tvShow: TVShow) =
        viewModelScope.launch {
            tvShowsDatabase!!.tvShowDao().addToWatchList(tvShow)
        }

    fun getTVShowFromWatchList(tvShowId: String) = tvShowsDatabase!!.tvShowDao().getTVShowFromWatchList(tvShowId)

    fun removeTVShowFromWatchList(tvShow: TVShow) =
        viewModelScope.launch {
            tvShowsDatabase!!.tvShowDao().removeFromWatchList(tvShow)
        }

}