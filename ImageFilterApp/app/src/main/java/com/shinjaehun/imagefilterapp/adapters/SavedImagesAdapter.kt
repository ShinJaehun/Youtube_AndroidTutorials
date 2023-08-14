package com.shinjaehun.imagefilterapp.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.imagefilterapp.databinding.ItemContainerSavedImageBinding
import com.shinjaehun.imagefilterapp.listeners.SavedImageListener
import java.io.File

class SavedImagesAdapter(
    private val savedImages: List<Pair<File, Bitmap>>,
    private val savedImageListener: SavedImageListener
): RecyclerView.Adapter<SavedImagesAdapter.SavedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        val binding = ItemContainerSavedImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SavedImageViewHolder(binding)
    }

    override fun getItemCount() = savedImages.size

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
        with(holder) {
            with(savedImages[position]) {
                binding.ivSaved.setImageBitmap(second)
                // 자 이거 pair니까 Bitmap을 second라는 변수로 받는거지...
                binding.ivSaved.setOnClickListener {
                    savedImageListener.onImageClicked(first)
                    // 파일을 넘깁니다..
                }
            }
        }
    }

    inner class SavedImageViewHolder(val binding: ItemContainerSavedImageBinding) : RecyclerView.ViewHolder(binding.root)
}