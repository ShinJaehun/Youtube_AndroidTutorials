package com.shinjaehun.notesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shinjaehun.notesapp.dao.database.NotesDatabase
import com.shinjaehun.notesapp.entities.Note
import kotlinx.coroutines.launch


private const val TAG = "NotesViewModel"

class NotesViewModel(
    val app: Application,
) : AndroidViewModel(app) {

    var notes: LiveData<List<Note>> = MutableLiveData()

    fun getNotes() {
        notes = NotesDatabase.getDatabase(app.applicationContext)?.noteDao()!!.getAllNotes()
    }
}