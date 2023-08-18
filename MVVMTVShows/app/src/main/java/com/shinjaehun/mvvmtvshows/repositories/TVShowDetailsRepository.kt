package com.shinjaehun.mvvmtvshows.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shinjaehun.mvvmtvshows.network.ApiClient
import com.shinjaehun.mvvmtvshows.network.ApiService
import com.shinjaehun.mvvmtvshows.responses.TVShowDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TVShowDetailsRepository"
class TVShowDetailsRepository {

    private var apiService: ApiService = ApiClient().getRetrofit().create(ApiService::class.java)

    fun getTVShowDetails(tvShowId: String) : LiveData<TVShowDetailsResponse> {
        val data: MutableLiveData<TVShowDetailsResponse> = MutableLiveData()
        apiService.getTVShowDetails(tvShowId).enqueue(object: Callback<TVShowDetailsResponse>{
            override fun onResponse(
                call: Call<TVShowDetailsResponse>,
                response: Response<TVShowDetailsResponse>,
            ) {
                data.value = response.body()
//                Log.i(TAG, "call: $call")
                Log.i(TAG, "response: $response")
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}