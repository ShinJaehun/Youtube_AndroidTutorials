package com.shinjaehun.mvvmtvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.repositories.MostPopularTVShowsRepository
import com.shinjaehun.mvvmtvshows.util.Resource
import com.shinjaehun.mvvmtvshows.viewmodels.MostPopularTVShowsViewModel
import com.shinjaehun.mvvmtvshows.viewmodels.TVShowsViewModelFactory


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MostPopularTVShowsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = MostPopularTVShowsRepository()
        val viewModelProviderFactory = TVShowsViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MostPopularTVShowsViewModel::class.java)

        viewModel.getMostPopularTVShows(1)

        viewModel.mostPopularTVShows.observe(this, Observer { response ->
            when(response) {
                is Resource.Success ->
                    response.data?.let { tvShowsResponse ->
                        Toast.makeText(applicationContext,
                            "Total pages ${tvShowsResponse.pages}", Toast.LENGTH_SHORT).show()
                    }
                is Resource.Error ->
                    response.message?.let { message ->
                        Toast.makeText(applicationContext,
                        "An error occured: $message", Toast.LENGTH_SHORT).show()
                    }

            }
        })

    }
}