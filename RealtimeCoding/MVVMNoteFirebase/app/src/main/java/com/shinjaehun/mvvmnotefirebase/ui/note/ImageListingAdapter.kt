package com.shinjaehun.mvvmnotefirebase.ui.note

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.shinjaehun.mvvmnotefirebase.databinding.ImageLayoutBinding

class ImageListingAdapter(
    val onCancelClicked: ((Int) -> Unit)? = null,
): RecyclerView.Adapter<ImageListingAdapter.MyViewHolder>() {

    private var urs: MutableList<Uri> = arrayListOf()

    inner class MyViewHolder(private val binding: ImageLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ur: Uri, position: Int) {
            if (ur.scheme == "content") {
                loadImageFromUri(binding.image, ur)
            } else {
                loadImageFromUrl(binding.image, ur.toString())
            }
            binding.cancel.setOnClickListener {
                onCancelClicked?.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ImageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return urs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val uri = urs[position]
        holder.bind(uri, position)
    }

    fun updateList(uris: List<Uri>) {
        this.urs = uris.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        urs.removeAt(position)
        notifyItemChanged(position)
    }

    fun loadImageFromUrl(imageView: ImageView, url: String) {
        imageView.load(url)
    }

    fun loadImageFromUri(imageView: ImageView, uri: Uri) {
        imageView.setImageURI(uri)
    }
}