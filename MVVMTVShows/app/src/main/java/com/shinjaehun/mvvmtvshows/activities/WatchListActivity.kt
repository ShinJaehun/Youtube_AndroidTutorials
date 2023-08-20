package com.shinjaehun.mvvmtvshows.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.adapters.WatchListAdapter
import com.shinjaehun.mvvmtvshows.databinding.ActivityWatchListBinding
import com.shinjaehun.mvvmtvshows.listeners.WatchListListener
import com.shinjaehun.mvvmtvshows.models.TVShow
import com.shinjaehun.mvvmtvshows.util.TempDataHolder
import com.shinjaehun.mvvmtvshows.viewmodels.WatchListViewModel
import com.shinjaehun.mvvmtvshows.viewmodels.WatchListViewModelFactory

private const val TAG = "WatchListActivity"
class WatchListActivity : AppCompatActivity(), WatchListListener {

    private lateinit var activityWatchListBinding: ActivityWatchListBinding
    private lateinit var watchListViewModel: WatchListViewModel
    private lateinit var watchListAdapter: WatchListAdapter
    private lateinit var watchlist : MutableList<TVShow>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWatchListBinding = ActivityWatchListBinding.inflate(layoutInflater)
        setContentView(activityWatchListBinding.root)

        doInitialization()
        loadWatchList()
    }

    private fun doInitialization() {
        val viewModelProviderFactory = WatchListViewModelFactory(application)
        watchListViewModel = ViewModelProvider(this, viewModelProviderFactory).get(WatchListViewModel::class.java)
        activityWatchListBinding.imageBack.setOnClickListener {
            onBackPressed()
        }
        watchlist = mutableListOf()
    }

    override fun onResume() {
        super.onResume()
        if (TempDataHolder.IS_WATCHLIST_UPDATED) {
            loadWatchList()
            TempDataHolder.IS_WATCHLIST_UPDATED = false
        }

    }

    private fun loadWatchList() {
        activityWatchListBinding.isLoading = true

//        watchListViewModel.loadWatchList()
//        Toast.makeText(applicationContext, "watchlist: " + watchListViewModel.tvShows?.size, Toast.LENGTH_SHORT).show()
        watchListViewModel.loadWatchList()!!.observe(this, Observer {
            activityWatchListBinding.isLoading = false
            Log.i(TAG, "watchlist: " + it.size)

            if (watchlist.size > 0) {
                watchlist.clear()
            }
            watchlist.addAll(it)
            watchListAdapter = WatchListAdapter(watchlist, LayoutInflater.from(this@WatchListActivity), this)
            activityWatchListBinding.watchListRecyclerView.adapter = watchListAdapter
            activityWatchListBinding.watchListRecyclerView.visibility = View.VISIBLE

        })


//        watchListViewModel.loadWatchList().also {
//            activityWatchListBinding.isLoading = false
//            if (watchListViewModel.tvShows != null) {
////                Toast.makeText(applicationContext, "watchlist: " + watchListViewModel.tvShows?.size, Toast.LENGTH_SHORT).show()
//                Log.i(TAG, "watchlist: " + watchListViewModel.tvShows?.size)
//            }
//        }
    }

    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }

    override fun removeTVShowFromWatchList(tvShow: TVShow, position: Int) {

        watchListViewModel.removeTVShowFromWatchList(tvShow).also {
            watchlist.removeAt(position)
            watchListAdapter.notifyItemRangeChanged(position, watchListAdapter.itemCount)
        }
//        이렇게 하면 java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionTVShowsViewHolder
//        watchListViewModel.removeTVShowFromWatchList(tvShow)
//        watchlist.removeAt(position)
//        watchListAdapter.notifyItemRangeChanged(position, watchListAdapter.itemCount)


    }
}