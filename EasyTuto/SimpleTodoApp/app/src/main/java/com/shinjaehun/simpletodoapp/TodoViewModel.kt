package com.shinjaehun.simpletodoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel: ViewModel() {

    val todoDao = MainApplication.todoDatabase.getTodoDao()

//    private var _todoList = MutableLiveData<List<Todo>>()
//    val todoList: LiveData<List<Todo>> = _todoList

    val todoList: LiveData<List<Todo>> = todoDao.getAllTodo()

//    init {
//        getAllTodo()
//    }

//    fun getAllTodo() {
//        _todoList.value = TodoManager.getAllTodo().reversed()
//    }

//    fun addTodo(title: String) {
//        TodoManager.addTodo(title)
//        getAllTodo()
//    }

    fun addTodo(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.addTodo(Todo(title = title, createdAt = Date.from(Instant.now())))
        }

    }

//    fun deleteTodo(id: Int){
//        TodoManager.deleteTodo(id)
//        getAllTodo()
//    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(id)
        }
    }
}