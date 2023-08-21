package com.shinjaehun.notesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shinjaehun.notesapp.database.NotesDatabase
import com.shinjaehun.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ADD_NOTE = 1
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.imageAddNoteMain.setOnClickListener {
            startActivityForResult(Intent(applicationContext, CreateNoteActivity::class.java), REQUEST_CODE_ADD_NOTE)
        }
    }

    override fun onStart() {
        super.onStart()
        getNotes() // 얘가 onCreate에 있으면... 
    }

    private fun getNotes() {
        GlobalScope.launch {
            NotesDatabase.getDatabase(applicationContext)?.noteDao()!!.getAllNotes().also {
                Log.i(TAG, it.toString())
            }
        }
    }


}