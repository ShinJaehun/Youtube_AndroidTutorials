package com.shinjaehun.mvvmtvshows.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.util.Log
import android.widget.Toast
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

    var mostPopularTVShows: LiveData<TVShowsResponse> = MutableLiveData()

    fun getMostPopularTVShows(page: Int) =
        viewModelScope.launch {
            if (hasInternetConnection()) {
                mostPopularTVShows = mostPopularTVShowsRepository.getMostPopularTVShowsRepo(page)
            } else {
                Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show()
                // 근데 이렇게 하니까 인터넷을 다시 활성화시킨 다음에 다시 시도해도... 걍 원래 상태 그대로임...
                // 그니까 뭔가 다른 처리가 필요함!
            }
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