package com.shinjaehun.notesapp.listeners

import com.shinjaehun.notesapp.entities.Note

interface NotesListener {
    fun onNoteClicked(note: Note, position: Int)
}