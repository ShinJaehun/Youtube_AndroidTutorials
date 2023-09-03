package com.shinjaehun.notesapp.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.notesapp.databinding.ItemContainerNoteBinding
import com.shinjaehun.notesapp.entities.Note
import com.shinjaehun.notesapp.listeners.NotesListener

class NotesAdapter(
//    private val notes: MutableList<Note>,
    private val inflater: LayoutInflater,
    private val notesListener: NotesListener
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(val binding: ItemContainerNoteBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemContainerNoteBinding.inflate(
            inflater,
            parent,
false)
        return NoteViewHolder(binding)
    }

//    override fun getItemCount() = notes.size
    override fun getItemCount() = allNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder) {
//            with(notes[position]) {
            with(allNotes[position]) {
                binding.tvTitle.text = this.title

                if (this.subtitle.trim().isEmpty()) {
                    binding.tvSubtitle.visibility = View.GONE
                } else {
                    binding.tvSubtitle.text = this.subtitle
                }

                binding.tvDateTime.text = this.dateTime

                val gradientDrawable = binding.layoutNote.background as GradientDrawable
                if (this.color != null) {
                    gradientDrawable.setColor(Color.parseColor(this.color))
                } else {
                    gradientDrawable.setColor(Color.parseColor("#333333"))
                }

                // 그냥 간단하게 표시하면 될 일을...
                if (this.imagePath != null) {
//                    binding.noteImagePreview.setImageBitmap(BitmapFactory.decodeFile(this.imagePath))
                    binding.noteImagePreview.setImageURI(Uri.parse(this.imagePath))
                    binding.noteImagePreview.visibility = View.VISIBLE
                } else {
                    binding.noteImagePreview.visibility = View.GONE
                }

                binding.layoutNote.setOnClickListener {
                    notesListener.onNoteClicked(this, position)
                }
            }
        }
    }
}