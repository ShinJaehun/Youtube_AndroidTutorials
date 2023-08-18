package com.shinjaehun.mvvmtvshows.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmtvshows.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(
    private val sliderImages: List<String>,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(val binding: ItemContainerSliderImageBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val binding = ItemContainerSliderImageBinding.inflate(
            inflater,
            parent,
            false)
        return ImageSliderViewHolder(binding)
    }

    override fun getItemCount() = sliderImages.size

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        with(holder) {
            with(sliderImages[position]) {
                binding.imageURL = this
            }
        }
    }
}