package com.example.todolist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskViewModel(private val repository: TaskItemRepository): ViewModel() {
//    var name = MutableLiveData<String>()
//    var desc = MutableLiveData<String>()

//    var taskItems = MutableLiveData<MutableList<TaskItem>>()

    var taskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()

//    init {
//        taskItems.value = mutableListOf()
//    }

    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if (!taskItem.isCompleted())
            taskItem.completedDateString = TaskItem.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }

//    fun addTaskItem(newTask: TaskItem) {
//        val list = taskItems.value
//        list!!.add(newTask)
//        taskItems.postValue(list)
//    }

//    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
//        val list = taskItems.value
//        val task = list!!.find {
//            it.id == id
//        }!!
//
//        task.name = name
//        task.desc = desc
//        task.dueTime = dueTime
//
//        taskItems.postValue(list)
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun setCompleted(taskItem: TaskItem) {
//        val list = taskItems.value
//        val task = list!!.find {
//            it.id == taskItem.id
//        }!!
//
//        if (task.completedDate == null) {
//            task.completedDate = LocalDate.now()
//        }
//
//        taskItems.postValue(list)
//    }
}

class TaskItemModelFactory(private val repository: TaskItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T
        throw IllegalArgumentException("unknown class for view model")
    }
}