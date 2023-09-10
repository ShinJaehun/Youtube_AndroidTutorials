package com.shinjaehun.mvvmnewsapp.repository.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.shinjaehun.mvvmnewsapp.model.Article
import com.shinjaehun.mvvmnewsapp.model.NewsResponse
import com.shinjaehun.mvvmnewsapp.repository.service.RetrofitClient
import com.shinjaehun.mvvmnewsapp.utils.Constants
import com.shinjaehun.mvvmnewsapp.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ArticleDataSource(val scope: CoroutineScope) : PageKeyedDataSource<Int, Article>(){
    // for breaking news
    val breakingNews: MutableLiveData<MutableList<Article>> = MutableLiveData()
    val breakingPageNumber = 1
    var breakingNewsResponse : NewsResponse? = null

    // for search news
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchPageNumber = 1
    var searchNewsResponse : NewsResponse? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        scope.launch {
            try {
                val response = RetrofitClient.api.getBreakingNews("kr", 1, Constants.API_KEY)
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it, null, 2)
                        }
                    }
                }
            } catch(exception: Exception) {
                Log.e("DataSource:: ", exception.message.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        try {
            scope.launch {
                val response = RetrofitClient.api.getBreakingNews("kr", params.requestedLoadSize, Constants.API_KEY)
                when {
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }
            }
        } catch(exception: Exception) {
            Log.e("DataSource:: ", exception.message.toString())
        }
    }


}