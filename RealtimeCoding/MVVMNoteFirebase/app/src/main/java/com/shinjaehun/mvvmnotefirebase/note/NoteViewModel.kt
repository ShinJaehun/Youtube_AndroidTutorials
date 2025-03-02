package com.shinjaehun.mvvmnotefirebase.note

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.data.repository.NoteRepository
import com.shinjaehun.mvvmnotefirebase.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val repository: NoteRepository
): ViewModel() {

    val _notes = MutableLiveData<UiState<List<Note>>>()
    val notes: LiveData<UiState<List<Note>>>
        get() = _notes

//    val _addNote = MutableLiveData<UiState<String>>()
//    val addNote: LiveData<UiState<String>>
//        get() = _addNote

    val _addNote = MutableLiveData<UiState<Pair<Note, String>>>()
    val addNote: LiveData<UiState<Pair<Note, String>>>
        get() = _addNote

    val _updateNote = MutableLiveData<UiState<String>>()
    val updateNote: LiveData<UiState<String>>
        get() = _updateNote

    val _deleteNote = MutableLiveData<UiState<String>>()
    val deleteNote: LiveData<UiState<String>>
        get() = _deleteNote

    fun getNotes() {
//        _notes.value = UiState.Loading
//        _notes.value = repository.getNotes() // 이걸 coroutine으로 처리하지 않으면 Loading 상태가 표시되지 않는다.

//        viewModelScope.launch {
//            _notes.value = UiState.Loading
//            delay(2000L) // delay 없으면 역시 표시되지 않음...;;
//            _notes.value = repository.getNotes()
//        }

        viewModelScope.launch {
            _notes.value = UiState.Loading

            repository.getNotes {
                _notes.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            _addNote.value = UiState.Loading

            repository.addNote(note) {
                _addNote.value = it
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            _updateNote.value = UiState.Loading

            repository.updateNote(note) {
                _updateNote.value = it
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            _deleteNote.value = UiState.Loading

            repository.deleteNote(note) {
                _deleteNote.value = it
            }
        }
    }
}