package com.shinjaehun.simpletodoapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var createdAt: Date
)

fun getFakeTodo(): List<Todo>{
    return listOf<Todo>(
        Todo(1, "First todo", Date.from(Instant.now())),
        Todo(2, "Second todo", Date.from(Instant.now())),
        Todo(3, "Third todo", Date.from(Instant.now())),
        Todo(4, "Fourth todo", Date.from(Instant.now())),
        Todo(5, "Fifth todo", Date.from(Instant.now()))
    )
}