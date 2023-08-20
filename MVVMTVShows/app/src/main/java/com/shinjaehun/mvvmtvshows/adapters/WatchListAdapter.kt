package com.shinjaehun.mvvmtvshows.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmtvshows.databinding.ItemContainerTvShowBinding
import com.shinjaehun.mvvmtvshows.listeners.TVShowsListener
import com.shinjaehun.mvvmtvshows.listeners.WatchListListener
import com.shinjaehun.mvvmtvshows.models.TVShow

class WatchListAdapter(
    private val tvShows: MutableList<TVShow>,
    private val inflater: LayoutInflater,
    private val watchListListener: WatchListListener
) : RecyclerView.Adapter<WatchListAdapter.TVShowsViewHolder>() {

    inner class TVShowsViewHolder(val binding: ItemContainerTvShowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsViewHolder {
        val binding = ItemContainerTvShowBinding.inflate(
            inflater,
            parent,
            false
        )
        return TVShowsViewHolder(binding)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {

        with(holder) {
            with(tvShows[position]) {
                binding.tvShow = this
                binding.executePendingBindings()
                binding.root.setOnClickListener {
                    watchListListener.onTVShowClicked(this)
                }
                binding.imageDelete.setOnClickListener {
                    watchListListener.removeTVShowFromWatchList(this, adapterPosition)
                }
                binding.imageDelete.visibility = View.VISIBLE
            }
        }
    }
}