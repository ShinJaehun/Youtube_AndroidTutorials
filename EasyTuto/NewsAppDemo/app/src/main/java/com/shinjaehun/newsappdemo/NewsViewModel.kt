package com.shinjaehun.newsappdemo

import android.net.Network
import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.newsappdemo.Constant.apiKey
import com.shinjaehun.newsappdemo.api.Article
import com.shinjaehun.newsappdemo.api.NetworkResponse
import com.shinjaehun.newsappdemo.api.NewsModel
import com.shinjaehun.newsappdemo.api.RetrofitInstance
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val TAG = "NewsViewModel"

class NewsViewModel: ViewModel() {

    private val newsApi = RetrofitInstance.newsApi

//    private val _newsResult = MutableLiveData<NetworkResponse<NewsModel>>()
//    val newsResult: LiveData<NetworkResponse<NewsModel>> = _newsResult

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> =_articles


    init {
        fetchNewsTopHeadline()
    }

    fun fetchNewsTopHeadline(category: String = "GENERAL"){
        Log.i(TAG, "API key: $apiKey")
//        val newsApiClient = NewsApiClient(apiKey)
//        val request = TopHeadlinesRequest.Builder().language("en").build()
//        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback {
//            override fun onSuccess(response: ArticleResponse?) {
//                response?.articles?.forEach {
//                    Log.i(TAG, "NewsAPI Response : ${it.title}")
//                }
//            }
//
//            override fun onFailure(throwable: Throwable?) {
//                Log.i(TAG, "NewsAPI Response Failed : ${throwable?.localizedMessage} ")
//            }
//
//        })

//        val request = SourcesRequest.Builder().language("en").country("us").build()
//        newsApiClient.getSources(request, object : NewsApiClient.SourcesCallback{
//            override fun onSuccess(response: SourcesResponse?) {
//                response?.sources?.forEach {
//                    Log.i(TAG, "NewsAPI Response : ${it.name}")
//                }
//            }
//
//            override fun onFailure(throwable: Throwable?) {
//                Log.i(TAG, "NewsAPI Response Failed : ${throwable?.localizedMessage} ")
//            }
//        })

//        viewModelScope.launch {
//            _newsResult.value = NetworkResponse.Loading
//            try {
//                val response = newsApi.getHeadlines(apikey = apiKey)
//                Log.i(TAG, "response: $response")
//                if(response.isSuccessful) {
//                    Log.i(TAG, "Response: ${response.body().toString()}")
//                    response.body()?.let {
//                        _newsResult.value = NetworkResponse.Success(it)
//                    }
//                } else {
//                    Log.i(TAG, "Error T: ${response.message()}")
//                    _newsResult.value = NetworkResponse.Error(response.message())
//                }
//            } catch (e: Exception) {
//                Log.i(TAG, "Error C: $e")
//                _newsResult.value = NetworkResponse.Error(e.toString())
//            }
//        }

        viewModelScope.launch {
            try {
                val response = newsApi.getHeadlines(category = category, apikey = apiKey)
                Log.i(TAG, "response: $response")
                if(response.isSuccessful) {
                    Log.i(TAG, "Response: ${response.body().toString()}")
                    response.body()?.let {
                        _articles.postValue(it.articles)
                    }
                } else {
                    Log.i(TAG, "Error T: ${response.message()}")

                }
            } catch (e: Exception) {
                Log.i(TAG, "Error C: $e")
            }
        }
    }

    fun fetchEverythingWithQuery(query: String) {
        viewModelScope.launch {
            try {
                val response = newsApi.getEverything(q = query, apikey = apiKey)
                if(response.isSuccessful) {
                    Log.i(TAG, "Response: ${response.body().toString()}")
                    response.body()?.let {
                        _articles.postValue(it.articles)
                    }
                } else {
                    Log.i(TAG, "Error T: ${response.message()}")

                }
            } catch (e: Exception) {
                Log.i(TAG, "Error C: $e")
            }
        }
    }
}