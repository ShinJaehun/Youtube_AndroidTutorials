package com.shinjaehun.mvvmnotefirebase.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.util.FirestoreTables
import com.shinjaehun.mvvmnotefirebase.util.UiState
import java.util.Date

class NoteRepositoryImpl(
    val database: FirebaseFirestore
): NoteRepository {
//    override fun getNotes(): UiState<List<Note>> {
//        val data = arrayListOf(
//            Note(
//                id = "asdf",
//                text = "Note 1",
//                date = Date()
//            ),
//            Note(
//                id = "dssdfg",
//                text = "Note 2",
//                date = Date()
//            ),
//            Note(
//                id = "sdfsd",
//                text = "Note 3",
//                date = Date()
//            )
//        )

//        val data = listOf<Note>()
//
//        if(data.isNullOrEmpty()) {
//            return UiState.Failure("Data is empty")
//        } else {
//            return UiState.Success(data)
//        }
//    }

    override fun getNotes(result: (UiState<List<Note>>) -> Unit) {
        database.collection(FirestoreTables.NOTE)
            .get()
            .addOnSuccessListener {
                val notes = arrayListOf<Note>()
                for(document in it) {
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                }
                result.invoke(
                    UiState.Success(notes)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }

    }

    override fun addNote(note: Note, result: (UiState<String>) -> Unit) {
//        database.collection(FirestoreTables.NOTE)
//            .add(note)
//            .addOnSuccessListener {
//                result.invoke(
//                    UiState.Success(it.id)
//                )
//            }
//            .addOnFailureListener {
//                result.invoke(
//                    UiState.Failure(
//                        it.localizedMessage
//                    )
//                )
//            }

        val document = database.collection(FirestoreTables.NOTE).document()
        note.id = document.id
        document.set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been created successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun updateNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirestoreTables.NOTE).document(note.id)
        document.set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been updated successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun deleteNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirestoreTables.NOTE).document(note.id)
        document.delete()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been deleted successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}