package com.shinjaehun.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shinjaehun.notesapp.databinding.ActivityCreateNoteBinding

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var activityCreateNoteBinding: ActivityCreateNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreateNoteBinding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(activityCreateNoteBinding.root)

        activityCreateNoteBinding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }
}