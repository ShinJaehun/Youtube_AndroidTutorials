package com.shinjaehun.notesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shinjaehun.notesapp.adapters.NotesAdapter
import com.shinjaehun.notesapp.databinding.ActivityMainBinding
import com.shinjaehun.notesapp.entities.Note
import com.shinjaehun.notesapp.viewmodels.NotesViewModel
import com.shinjaehun.notesapp.viewmodels.NotesViewModelFactory

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
        observeNotes()
    }

//    override fun onStart() {
//        super.onStart()
//        obs() // 얘가 onCreate에 있으면...
//    }

    private fun observeNotes() {
        notesViewModel.notes.observe(this, Observer {
            if (notes.size == 0) {
            // We checked if the note list is empty it means the app is just started since we have declared it as a global variable, in this case, we are adding all notes from the database to this note list and notify the adapter about the new dataset.
                notes.addAll(it)
                notesAdapter.notifyDataSetChanged()
            } else {
            // In another case, if the note list is not empty then it means notes are already loaded from the database so we are just adding only the latest note to the note list and notify adapter about new note inserted. And last we scrolled our recycler view to the top
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