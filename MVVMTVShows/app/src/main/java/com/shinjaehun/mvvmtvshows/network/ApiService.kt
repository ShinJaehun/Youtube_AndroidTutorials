package com.shinjaehun.mvvmtvshows.network

import com.shinjaehun.mvvmtvshows.models.TVShowDetails
import com.shinjaehun.mvvmtvshows.responses.TVShowDetailsResponse
import com.shinjaehun.mvvmtvshows.responses.TVShowsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("most-popular")
    fun getMostPopularTVShows(
        // suspend 안 붙여서 오류
        // https://stackoverflow.com/questions/32269064/unable-to-create-call-adapter-for-class-example-simple
        @Query("page")
        page: Int = 1
    ): Call<TVShowsResponse>

    @GET("show-details")
    fun getTVShowDetails(
        @Query("q")
        tvShowId: String
    ): Call<TVShowDetailsResponse>

    @GET("search")
    fun searchTVShow(
        @Query("q")
        query: String,
        @Query("page")
        page: Int
    ): Call<TVShowsResponse>
}