package com.shinjaehun.mvvmnotefirebase.data.repository

import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.util.UiState

interface NoteRepository {

//    fun getNotes(): UiState<List<Note>>

    // 얘네가 suspend가 아닌 이유는 FB가 그냥 그렇게 알아서 처리한다는건가?????????
    fun getNotes(result: (UiState<List<Note>>) -> Unit)
//    fun addNote(note: Note, result: (UiState<String>) -> Unit)
    fun addNote(note: Note, result: (UiState<Pair<Note, String>>) -> Unit)
    fun updateNote(note: Note, result: (UiState<String>) -> Unit)
    fun deleteNote(note: Note, result: (UiState<String>) -> Unit)
}