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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "NotesAdapter"

class NotesAdapter(
//    private val notes: MutableList<Note>,
    private val inflater: LayoutInflater,
    private val notesListener: NotesListener,
//    private val notesSource: ArrayList<Note>
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val allNotes = ArrayList<Note>()
    private var timer: Timer? = null

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

    fun searchNotes(searchKeyword : String, notesSource: List<Note>) {
        var searchNotes : ArrayList<Note> = arrayListOf()
        timer = Timer()

        timer!!.schedule(object: TimerTask(){
            override fun run() {

                if (searchKeyword.trim().isEmpty()) {
                    searchNotes = notesSource as ArrayList<Note>
                } else {
                    for (note in notesSource) {
                        if (note.title.toLowerCase().contains(searchKeyword.toLowerCase())
                            || note.subtitle.toLowerCase().contains(searchKeyword.toLowerCase())
                            || note.noteText.toLowerCase().contains(searchKeyword.toLowerCase())) {
                            searchNotes.add(note)
                        }
                    }
                }

                GlobalScope.launch(Dispatchers.Main) {
                    updateList(searchNotes)
                }
            }
        }, 500)
    }

    fun cancelTimer() {
        if (timer != null) {
            timer!!.cancel()
        }
    }
}