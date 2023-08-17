package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.mvvmtvshows.MVVMTVShowsApplication
import com.shinjaehun.mvvmtvshows.repositories.MostPopularTVShowsRepository
import com.shinjaehun.mvvmtvshows.responses.TVShowsResponse
import com.shinjaehun.mvvmtvshows.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

const val TAG = "ViewModel"
class MostPopularTVShowsViewModel(
    app: Application,
    private val mostPopularTVShowsRepository: MostPopularTVShowsRepository
) : AndroidViewModel(app) {

//    val mostPopularTVShows: MutableLiveData<Resource<TVShowsResponse>> = MutableLiveData()
//    var page = 0
//    private var tvShowsResponse: TVShowsResponse? = null

//    fun getMostPopularTVShows(page: Int) = viewModelScope.launch {
//        safeMostPopularTVShowCall(page)
//    }
    var mostPopularTVShows: LiveData<TVShowsResponse> = MutableLiveData()

//    fun getMostPopularTVShows(page: Int) : LiveData<TVShowsResponse> {
////        safeMostPopularTVShowCall()
//        return mostPopularTVShowsRepository.getMostPopularTVShowsRepo(page)
//    }

    fun getMostPopularTVShows(page: Int) = viewModelScope.launch {
//        safeMostPopularTVShowCall()
        mostPopularTVShows = mostPopularTVShowsRepository.getMostPopularTVShowsRepo(page)
    }


//    private suspend fun safeMostPopularTVShowCall(page: Int) {
//        try {
//            if (hasInternetConnection()) {
//                val response = mostPopularTVShowsRepository.getMostPopularTVShows(page)
//                Log.i(TAG, "$response")
//                mostPopularTVShows.postValue(handleMostPopularTVShowsResponse(response))
//            } else {
//                mostPopularTVShows.postValue(Resource.Error("No internet connection"))
//            }
//        } catch (e: Exception) {
//            Log.i(TAG, e.message.toString())
//        }
//    }

//    private suspend fun safeMostPopularTVShowCall() {
//        try {
//            if (hasInternetConnection()) {
//                page++
//                Log.i(TAG, "current page : $page")
//
//                val response = mostPopularTVShowsRepository.getMostPopularTVShows(page)
//
//                Log.i(TAG, "$response")
//                mostPopularTVShows.postValue(handleMostPopularTVShowsResponse(response))
//            } else {
//                mostPopularTVShows.postValue(Resource.Error("No internet connection"))
//            }
//        } catch (e: Exception) {
//            Log.i(TAG, e.message.toString())
//        }
//    }
//
//    private fun handleMostPopularTVShowsResponse(response: Response<TVShowsResponse>): Resource<TVShowsResponse>? {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//
//
//                if (tvShowsResponse == null) {
//                    tvShowsResponse = resultResponse
//                }
//                return Resource.Success(tvShowsResponse ?: resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }

    // 원래는 이렇게 긴데...
//    private fun hasInternetConnection(): Boolean {
//        val connectivityManager = getApplication<MVVMTVShowsApplication>().getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val activeNetwork = connectivityManager.activeNetwork ?: return false
//            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//            return when {
//                capabilities.hasTransport(TRANSPORT_WIFI) -> true
//                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
//                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
//                else -> false
//            }
//        } else {
//            connectivityManager.activeNetworkInfo?.run {
//                return when(type) {
//                    TYPE_WIFI -> true
//                    TYPE_MOBILE -> true
//                    TYPE_ETHERNET -> true
//                    else -> false
//                }
//            }
//        }
//        return false
//    }
    private fun hasInternetConnection(): Boolean {
        // 자동으로 뭔가 이렇게 줄여놨음!!
        val connectivityManager = getApplication<MVVMTVShowsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}