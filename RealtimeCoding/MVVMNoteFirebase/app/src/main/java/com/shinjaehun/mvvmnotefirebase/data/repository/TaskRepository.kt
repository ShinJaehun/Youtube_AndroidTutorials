package com.shinjaehun.mvvmnotefirebase.data.repository

import com.shinjaehun.mvvmnotefirebase.data.model.Task
import com.shinjaehun.mvvmnotefirebase.data.model.User
import com.shinjaehun.mvvmnotefirebase.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit)
    fun updateTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit)
    fun deleteTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit)
    fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit)
}