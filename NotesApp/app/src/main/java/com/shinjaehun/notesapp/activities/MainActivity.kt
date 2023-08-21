package com.shinjaehun.notesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shinjaehun.notesapp.adapters.NotesAdapter
import com.shinjaehun.notesapp.database.NotesDatabase
import com.shinjaehun.notesapp.databinding.ActivityMainBinding
import com.shinjaehun.notesapp.entities.Note
import com.shinjaehun.notesapp.viewmodels.NotesViewModel
import com.shinjaehun.notesapp.viewmodels.NotesViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ADD_NOTE = 1
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var notesViewModel: NotesViewModel
    private val notes: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.imageAddNoteMain.setOnClickListener {
//            startActivityForResult(Intent(applicationContext, CreateNoteActivity::class.java), REQUEST_CODE_ADD_NOTE)
            startActivity(Intent(applicationContext, CreateNoteActivity::class.java))
        }

        val viewModelProviderFactory = NotesViewModelFactory(application)
        notesViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NotesViewModel::class.java)

        notesAdapter = NotesAdapter(notes, layoutInflater)
        activityMainBinding.notesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        activityMainBinding.notesRecyclerView.adapter = notesAdapter

        notesViewModel.getNotes()
        obs()
    }

//    override fun onStart() {
//        super.onStart()
//        obs() // 얘가 onCreate에 있으면...
//    }

    private fun obs() {
        notesViewModel.notes.observe(this, Observer {
            if (notes.size == 0) {
                notes.addAll(it)
                notesAdapter.notifyDataSetChanged()
            } else {
                notes.add(0, it[0])
                notesAdapter.notifyItemInserted(0)
            }
            activityMainBinding.notesRecyclerView.smoothScrollToPosition(0)
        })

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
//            getNotes()
//        }
//    }
}