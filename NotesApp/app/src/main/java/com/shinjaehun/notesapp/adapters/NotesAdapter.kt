package com.shinjaehun.notesapp.adapters

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.notesapp.databinding.ItemContainerNoteBinding
import com.shinjaehun.notesapp.entities.Note
import com.shinjaehun.notesapp.listeners.NotesListener

private const val TAG = "NotesAdapter"

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

                if (this.imagePath != null && this.imagePath != "") {
                    // CreateNoteActivity에서 imageDeleteImage click 이벤트에서 selectedImagePath = ""
//                    Log.i(TAG, "${this.title} imagePath : ${this.imagePath.toString()}")
//                    binding.noteImagePreview.setImageBitmap(BitmapFactory.decodeFile(this.imagePath))
                    // 그냥 간단하게 표시하면 될 일을...
                    binding.noteImagePreview.setImageURI(Uri.parse(this.imagePath))
                    // 헐 이렇게 하면 CreateNoteActivity에서 이미지를 삭제하고 다시 메인으로 돌아왔을 때 View.GONE이 되지는 않는거 같다...
                    // 빈 이미지로 남아 있음... 뭐가 문제지?

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