package com.shinjaehun.mvvmnotefirebase.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.databinding.ItemNoteLayoutBinding
import com.shinjaehun.mvvmnotefirebase.util.addChip
import com.shinjaehun.mvvmnotefirebase.util.hide
import java.text.SimpleDateFormat

class NoteListingAdapter(
    val onItemClicked: (Int, Note) -> Unit,
//    val onEditClicked: (Int, Note) -> Unit,
//    val onDeleteClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    private var list: MutableList<Note> = arrayListOf()
    val sdf = SimpleDateFormat("yyyy MMM dd")

    inner class MyViewHolder(val binding: ItemNoteLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
//            binding.noteIdValue.setText(item.id)
//            binding.msg.setText(item.text)
//            binding.edit.setOnClickListener { onEditClicked.invoke(adapterPosition, item) }
//            binding.delete.setOnClickListener { onDeleteClicked.invoke(adapterPosition, item) }
//            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition, item) }

            binding.title.setText(item.title)
            binding.date.setText(sdf.format(item.date))
            binding.tags.apply {
                if (item.tags.isNullOrEmpty()) {
                    hide()
                } else {
                    removeAllViews()
                    if (item.tags.size > 2) {
                       item.tags.subList(0, 2).forEach { tag -> addChip(tag) }
                        addChip("+${item.tags.size - 2}")
                    } else {
                        item.tags.forEach { tag -> addChip(tag) }
                    }
                }
            }
            binding.desc.apply {
                if (item.description.length > 120) {
                    text = "${item.description.substring(0,120)}..."
                } else {
                    text = item.description
                }
            }
            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition, item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<Note>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemChanged(position)
    }
}