package com.shinjaehun.notesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shinjaehun.notesapp.database.NotesDatabase
import com.shinjaehun.notesapp.databinding.ActivityCreateNoteBinding
import com.shinjaehun.notesapp.entities.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var activityCreateNoteBinding: ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreateNoteBinding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(activityCreateNoteBinding.root)

        activityCreateNoteBinding.imageBack.setOnClickListener {
            onBackPressed()
        }

        activityCreateNoteBinding.textDateTime.text = SimpleDateFormat("yyyy MMMM dd, EEEE, HH:mm a", Locale.getDefault()).format(Date())

        activityCreateNoteBinding.imageSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = activityCreateNoteBinding.etNoteTitle.text.toString()
        val subtitle = activityCreateNoteBinding.etNoteSubtitle.text.toString()
        val noteText = activityCreateNoteBinding.etNote.text.toString()
        val dateTime = activityCreateNoteBinding.textDateTime.text.toString()


        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show()
            return
        } else if (subtitle.trim().isEmpty()) {
            Toast.makeText(this, "Note subtitle can't be empty", Toast.LENGTH_SHORT).show()
            return
        } else if (noteText.trim().isEmpty()) {
            Toast.makeText(this, "Notes can't be empty", Toast.LENGTH_SHORT).show()
            return
        }

        var note = Note(
            title = title,
            subtitle = subtitle,
            noteText = noteText,
            dateTime = dateTime
        )

        GlobalScope.launch {
            NotesDatabase.getDatabase(applicationContext)?.noteDao()!!.insertNote(note).also {
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}