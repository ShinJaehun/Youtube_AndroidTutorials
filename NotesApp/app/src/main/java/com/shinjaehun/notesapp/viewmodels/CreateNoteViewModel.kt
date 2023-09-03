package com.shinjaehun.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.notesapp.database.NotesDatabase
import com.shinjaehun.notesapp.entities.Note
import kotlinx.coroutines.launch

class CreateNoteViewModel(
    val app: Application
) : AndroidViewModel(app) {

    fun insertNote(note: Note) =
        viewModelScope.launch {
            NotesDatabase.getDatabase(app)?.noteDao()!!.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            NotesDatabase.getDatabase(app)?.noteDao()!!.deleteNote(note)
        }
}