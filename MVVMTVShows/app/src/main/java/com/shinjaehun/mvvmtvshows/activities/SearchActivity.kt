package com.shinjaehun.mvvmtvshows.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmtvshows.adapters.TVShowsAdapter
import com.shinjaehun.mvvmtvshows.databinding.ActivitySearchBinding
import com.shinjaehun.mvvmtvshows.listeners.TVShowsListener
import com.shinjaehun.mvvmtvshows.models.TVShow
import com.shinjaehun.mvvmtvshows.repositories.SearchTVShowRepository
import com.shinjaehun.mvvmtvshows.viewmodels.SearchViewModel
import com.shinjaehun.mvvmtvshows.viewmodels.SearchViewModelFactory
import kotlinx.coroutines.coroutineScope
import java.util.Timer
import java.util.TimerTask

class SearchActivity : AppCompatActivity(), TVShowsListener {

    private lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private var tvShows : MutableList<TVShow> = mutableListOf()
    private lateinit var tvShowsAdapter: TVShowsAdapter
    private var currentPage = 1
    private var totalAvailablePages = 1
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(activitySearchBinding.root)
        doInitialization()
    }

    private fun doInitialization() {

        val repository = SearchTVShowRepository()
        val viewModelProviderFactory = SearchViewModelFactory(application, repository)
        searchViewModel = ViewModelProvider(this, viewModelProviderFactory).get(SearchViewModel::class.java)
        activitySearchBinding.imageBack.setOnClickListener {
            onBackPressed()
        }

        activitySearchBinding.tvShowsRecyclerView.setHasFixedSize(true)
        tvShowsAdapter = TVShowsAdapter(tvShows, layoutInflater, this)
        activitySearchBinding.tvShowsRecyclerView.adapter = tvShowsAdapter

        activitySearchBinding.inputSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(editable: Editable?) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object: TimerTask(){
                        override fun run() {
                            runOnUiThread {
                                currentPage = 1
                                totalAvailablePages = 1
                                searchTVShow(editable.toString())
                            }
                        }
                    }, 800)
                } else {
                    tvShows.clear()
                    tvShowsAdapter.notifyDataSetChanged()
                }
            }
        })

        activitySearchBinding.tvShowsRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activitySearchBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (!activitySearchBinding.inputSearch.text.toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage++
                            searchTVShow(activitySearchBinding.inputSearch.text.toString())
                        }
                    }
                }
            }
        })

        activitySearchBinding.inputSearch.requestFocus()

    }

    private fun searchTVShow(query: String) {
        toggleLoading()
        searchViewModel.searchTVShow(query, currentPage).observe(this, Observer { response ->
            toggleLoading()
            if (response != null) {
                totalAvailablePages = response.pages

                if (response.tv_shows != null) {
                    val oldCount = tvShows.size
                    tvShows.addAll(response.tv_shows)
                    tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
                }
            }
        })
    }

    private fun toggleLoading() {
        if (currentPage == 1) {
            activitySearchBinding.isLoading = (activitySearchBinding.isLoading == null) || !activitySearchBinding.isLoading!!
        } else {
            activitySearchBinding.isLoadingMore = activitySearchBinding.isLoadingMore == null || !activitySearchBinding.isLoadingMore!!
        }
    }

    override fun onTVShowClick(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }
}