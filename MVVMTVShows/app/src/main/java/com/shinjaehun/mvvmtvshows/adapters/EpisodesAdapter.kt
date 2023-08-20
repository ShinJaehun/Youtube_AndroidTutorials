package com.shinjaehun.mvvmtvshows.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmtvshows.databinding.ItemContainerEpisodeBinding
import com.shinjaehun.mvvmtvshows.models.Episode

private const val TAG = "EpisodesAdapter"
class EpisodesAdapter(
    private val episodes : List<Episode>,
    private val inflater: LayoutInflater
): RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {
   inner class EpisodeViewHolder(var binding: ItemContainerEpisodeBinding) :
            RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemContainerEpisodeBinding.inflate(
            inflater,
            parent,
            false)
        return EpisodeViewHolder(binding)
    }

    override fun getItemCount() = episodes.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
            with(holder) {
            with(episodes[position]) {
                    var title = "S"
                var season : String = this.season
                if (season.length == 1) {
                    season = "0$season"
                }
                var episodeNumber = this.episode
                if (episodeNumber.length == 1) {
                    episodeNumber = "0$episodeNumber"
                }
                episodeNumber = "E$episodeNumber"
                title = "${title}${season}${episodeNumber}"
                binding.title = title
                binding.name = this.name
                binding.airDate = this.air_date
            }
        }


    }


}