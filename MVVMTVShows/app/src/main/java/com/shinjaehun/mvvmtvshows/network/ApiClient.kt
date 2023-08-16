package com.shinjaehun.mvvmtvshows.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
//    private lateinit var retrofit: Retrofit
//    private fun getRetrofit() : Retrofit {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                .baseUrl("https://www.episodate.com/api")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return retrofit
//    }

    companion object {
        private val retrofit by lazy {
            // 굳이 OkHttpClient 사용하지 않아도 정상적으로 받아오는데???
            Retrofit.Builder()
                .baseUrl("https://www.episodate.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
//        private val retrofit by lazy {
//            val logging = HttpLoggingInterceptor()
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()
//            Retrofit.Builder()
//                .baseUrl("https://www.episodate.com/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//            // baseUrl 끝에 '/' 안 붙여서 오류...
//        }

        val api by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}