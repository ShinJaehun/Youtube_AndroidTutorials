package com.shinjaehun.mvvmnotefirebase.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.shinjaehun.mvvmnotefirebase.data.model.Task
import com.shinjaehun.mvvmnotefirebase.data.model.User
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

    override fun updateTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).child(task.id)
        reference
            .setValue(task)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task, "Task has been updated successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun deleteTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).child(task.id)
        reference
            .removeValue()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task, "Task has been deleted successfully"))
                )
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).orderByChild("user_id").equalTo(user?.id)
        reference.get()
            .addOnSuccessListener {
                val tasks = arrayListOf<Task?>()
                for (item in it.children) {
                    val task = item.getValue(Task::class.java)
                    tasks.add(task)
                }
                result.invoke(UiState.Success(tasks.filterNotNull()))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }
}