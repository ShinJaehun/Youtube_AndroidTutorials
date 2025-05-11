package com.shinjaehun.notesapp

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import io.realm.Realm

class AddNoteActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val titleInput = findViewById<EditText>(R.id.titleinput)
        val descriptionInput = findViewById<EditText>(R.id.descriptioninput)
        val saveBtn = findViewById<MaterialButton>(R.id.savebtn)

        Realm.init(applicationContext)
        val realm = Realm.getDefaultInstance()

        saveBtn.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val createdTime = System.currentTimeMillis()

            realm.beginTransaction()
            val note = realm.createObject(Note::class.java)
            note.title = title
            note.description = description
            note.createdTime = createdTime
            realm.commitTransaction()
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}