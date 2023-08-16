package com.shinjaehun.mvvmtvshows.repositories

import androidx.lifecycle.LiveData
import com.shinjaehun.mvvmtvshows.network.ApiClient
import com.shinjaehun.mvvmtvshows.network.ApiService
import com.shinjaehun.mvvmtvshows.responses.TVShowsResponse
import retrofit2.Response

class MostPopularTVShowsRepository {

//    private lateinit var apiService: ApiService
//
//    init {
//        apiService = ApiClient.
//    }

    suspend fun getMostPopularTVShows(page: Int) : Response<TVShowsResponse> =
        ApiClient.api.getMostPopularTVShows(page)
}