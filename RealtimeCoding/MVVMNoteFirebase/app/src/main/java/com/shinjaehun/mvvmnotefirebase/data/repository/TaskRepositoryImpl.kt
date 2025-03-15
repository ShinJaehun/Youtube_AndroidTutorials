package com.shinjaehun.mvvmnotefirebase.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.shinjaehun.mvvmnotefirebase.data.model.Task
import com.shinjaehun.mvvmnotefirebase.util.FireDatabase
import com.shinjaehun.mvvmnotefirebase.util.UiState

class TaskRepositoryImpl(val database: FirebaseDatabase): TaskRepository {
    override fun addTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).push()
        val uniqueKey = reference.key ?: "invalid"
        task.id = uniqueKey
        reference
            .setValue(task)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task, "Task has been created successfully"))
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