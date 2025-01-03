package com.shinjaehun.mvvmtvshows.adapters

import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.databinding.ItemContainerTvShowBinding
import com.shinjaehun.mvvmtvshows.listeners.TVShowsListener
import com.shinjaehun.mvvmtvshows.models.TVShow
import java.util.Collections.addAll

private const val TAG = "TVShowsAdapter"
class TVShowsAdapter(
    private val tvShows: MutableList<TVShow>,
    private val inflater: LayoutInflater,
    private val tvShowsListener: TVShowsListener
) : RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder>() {

    inner class TVShowsViewHolder(val binding: ItemContainerTvShowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsViewHolder {
        val binding = ItemContainerTvShowBinding.inflate(
            inflater,
            parent,
            false)
        return TVShowsViewHolder(binding)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {

        with(holder) {
            with(tvShows[position]) {
                binding.tvShow = this
                binding.executePendingBindings()
                binding.root.setOnClickListener {
                    tvShowsListener.onTVShowClick(this)
                }
            }
        }
    }
}