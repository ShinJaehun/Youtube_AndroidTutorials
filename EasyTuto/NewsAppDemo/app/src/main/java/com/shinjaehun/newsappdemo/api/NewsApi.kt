package com.shinjaehun.newsappdemo.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        countryCode: String = "us",
        @Query("category")
        category: String,
        @Query("apiKey")
        apikey: String
    ): Response<NewsModel>

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q")
        q: String,
        @Query("apiKey")
        apikey: String
    ): Response<NewsModel>
}