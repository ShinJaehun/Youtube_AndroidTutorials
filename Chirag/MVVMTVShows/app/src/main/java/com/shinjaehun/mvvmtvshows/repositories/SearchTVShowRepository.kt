package com.shinjaehun.mvvmtvshows.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shinjaehun.mvvmtvshows.network.ApiClient
import com.shinjaehun.mvvmtvshows.network.ApiService
import com.shinjaehun.mvvmtvshows.responses.TVShowsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchTVShowRepository {

    private var apiService: ApiService = ApiClient().getRetrofit().create(ApiService::class.java)


    fun searchTVShow(query: String, page: Int) : LiveData<TVShowsResponse> {
        val data : MutableLiveData<TVShowsResponse> = MutableLiveData()

        apiService.searchTVShow(query, page).enqueue(object: Callback<TVShowsResponse> {
            override fun onResponse(
                call: Call<TVShowsResponse>,
                response: Response<TVShowsResponse>,
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowsResponse>, t: Throwable) {
                data.value = null
            }

        })

        return data
    }
}