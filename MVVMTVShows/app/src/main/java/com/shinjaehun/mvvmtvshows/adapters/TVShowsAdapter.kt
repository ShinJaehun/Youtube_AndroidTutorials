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

const val TAG = "Adapter"
class TVShowsAdapter(
    private val tvShows: MutableList<TVShow>,
    private val inflater: LayoutInflater,
    private val tvShowsListener: TVShowsListener
) : RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder>() {

//    private val tvShows: MutableList<TVShow> = mutableListOf()
    inner class TVShowsViewHolder(val binding: ItemContainerTvShowBinding) : RecyclerView.ViewHolder(binding.root)
//    fun setTVShows() {
//        this.tvShows.apply {
//            clear()
////            addAll(tv_Shows)
//        }
//        notifyDataSetChanged()
//    }
//
//    fun addTVShows(tv_Shows: List<TVShow>) {
////        this.tvShows.addAll(tv_Shows)
//        val old_count = this.tvShows.size
//        Log.i(TAG, "position start: $old_count")
//        this.tvShows.apply {
//            addAll(tv_Shows)
//        }
//        Log.i(TAG, "itemCount: ${this.tvShows.size}")
//        notifyItemRangeInserted(old_count, this.tvShows.size)
//
////        notifyDataSetChanged()
//
//        for (ts in tvShows) {
//            Log.i(TAG, "${ts.name}")
//        }
//
////        this.tvShows.clear()
//    }

//    private val differCallback = object: DiffUtil.ItemCallback<TVShow>(){
//        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsViewHolder {
        val binding = ItemContainerTvShowBinding.inflate(
            inflater,
            parent,
            false)
        return TVShowsViewHolder(binding)
    }

    override fun getItemCount() = tvShows.size
//    override fun getItemCount() = differ.currentList.size
    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {
//        val tv_shows = differ.currentList[position]


        with(holder) {
            with(tvShows[position]) {
//            with(tv_shows) {
                binding.tvShow = this
                binding.executePendingBindings()
                binding.root.setOnClickListener {
                    tvShowsListener.onTVShowClick(this)
                }
            }
        }
    }
}