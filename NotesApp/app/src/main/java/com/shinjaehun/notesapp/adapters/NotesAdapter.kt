package com.shinjaehun.notesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.notesapp.databinding.ItemContainerNoteBinding
import com.shinjaehun.notesapp.entities.Note

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    inner class NoteViewHolder(val binding: ItemContainerNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemContainerNoteBinding.inflate(
            inflater,
            parent,
false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder) {
            with(notes[position]) {
                binding.tvTitle.text = this.title

                if (this.subtitle.trim().isEmpty()) {
                    binding.tvSubtitle.visibility = View.GONE
                } else {
                    binding.tvSubtitle.text = this.subtitle
                }

                binding.tvDateTime.text = this.dateTime
            }
        }
    }


}