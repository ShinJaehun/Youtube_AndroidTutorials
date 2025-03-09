package com.shinjaehun.mvvmnotefirebase.data.repository

import android.net.Uri
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.data.model.User
import com.shinjaehun.mvvmnotefirebase.util.FirestoreCollection
import com.shinjaehun.mvvmnotefirebase.util.FirestoreDocumentField
import com.shinjaehun.mvvmnotefirebase.util.StorageConstants.NOTE_IMAGES
import com.shinjaehun.mvvmnotefirebase.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    val database: FirebaseFirestore,
    val storageReference: StorageReference
): NoteRepository {

    override fun getNotes(user: User?, result: (UiState<List<Note>>) -> Unit) {
        database.collection(FirestoreCollection.NOTE)
            .whereEqualTo(FirestoreDocumentField.USER_ID, user?.id)
            .orderBy(FirestoreDocumentField.DATE, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val notes = arrayListOf<Note>()
                for (document in it) {
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                }
                result.invoke(UiState.Success(notes))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun addNote(note: Note, result: (UiState<Pair<Note, String>>) -> Unit) {
        val document = database.collection(FirestoreCollection.NOTE).document()
        note.id = document.id
        document.set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(note, "Note has been created successfully"))
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
        val document = database.collection(FirestoreCollection.NOTE).document(note.id)
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
        val document = database.collection(FirestoreCollection.NOTE).document(note.id)
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

    override suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit) {
        try {
            val uri: Uri = withContext(Dispatchers.IO) {
                storageReference.child(NOTE_IMAGES).child(fileUri.lastPathSegment ?: "${System.currentTimeMillis()}")
                    .putFile(fileUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
            onResult.invoke(UiState.Success(uri))
        } catch (e: FirebaseException) {
            onResult.invoke(UiState.Failure(e.message.toString()))
        } catch (e: Exception) {
            onResult.invoke(UiState.Failure(e.message.toString()))
        }
    }

    override suspend fun uploadMultipleFiles(fileUris: List<Uri>, onResult: (UiState<List<Uri>>) -> Unit) {
        try {
            val uris: List<Uri> = withContext(Dispatchers.IO) {
                fileUris.map { imageUri ->
                    async {
                        storageReference.child(NOTE_IMAGES).child(imageUri.lastPathSegment ?: "${System.currentTimeMillis()}")
                            .putFile(imageUri)
                            .await()
                            .storage
                            .downloadUrl
                            .await()
                    }
                }.awaitAll()
            }
            onResult.invoke(UiState.Success(uris))
        } catch (e: FirebaseException) {
            onResult.invoke(UiState.Failure(e.message.toString()))
        } catch (e: Exception) {
            onResult.invoke(UiState.Failure(e.message.toString()))
        }
    }
}