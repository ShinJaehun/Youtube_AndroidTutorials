package com.shinjaehun.notesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shinjaehun.notesapp.adapters.NotesAdapter
import com.shinjaehun.notesapp.databinding.ActivityMainBinding
import com.shinjaehun.notesapp.entities.Note
import com.shinjaehun.notesapp.listeners.NotesListener
import com.shinjaehun.notesapp.viewmodels.NotesViewModel
import com.shinjaehun.notesapp.viewmodels.NotesViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NotesListener {

    companion object {
        private const val REQUEST_CODE_ADD_NOTE = 1
        private const val REQUEST_CODE_UPDATE_NOTE = 2
        private const val REQUEST_CODE_SHOW_NOTES = 3
    }

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var notesAdapter : NotesAdapter
    private lateinit var notesViewModel: NotesViewModel
//    private var notes: MutableList<Note> = mutableListOf()

    private var timer: Timer? = null


    private var noteClickedPosition: Int = -1
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

//        notesAdapter = NotesAdapter(notes, layoutInflater, this)
        notesAdapter = NotesAdapter(layoutInflater, this)
        activityMainBinding.notesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        activityMainBinding.notesRecyclerView.adapter = notesAdapter

        notesViewModel.getNotes()

        notesViewModel.notes.observe(this, Observer { list ->
            // 그냥 이렇게 하는게 리스트를 깔끔하게 한번만 호출함
            list?.let {

//                it.forEachIndexed { index, note ->
//                    Log.i(TAG, "notes[$index]: ${note.title}")
//                }

                notesAdapter.updateList(it)
            }
        })

//        notesViewModel.getNotes()
//        observeNotes(REQUEST_CODE_SHOW_NOTES)

        activityMainBinding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                notesAdapter.cancelTimer()
            }

            override fun afterTextChanged(s: Editable?) {
                notesViewModel.notes.observe(this@MainActivity, Observer { list ->
                    list?.let {
                        list.forEachIndexed { index, note ->
                            Log.i(TAG, "viewModel notes[$index]: ${note.title}")
                        }
                        notesAdapter.searchNotes(s.toString(), list)

                    }
                })
            }
        })
    }

//    override fun onStart() {
//        super.onStart()
//        obs() // 얘가 onCreate에 있으면...
//    }

    override fun onNoteClicked(note: Note, position: Int) {
        noteClickedPosition = position
//        Log.i(TAG, "position : $noteClickedPosition")
        val intent = Intent(applicationContext, CreateNoteActivity::class.java)
        intent.putExtra("isViewOrUpdate", true)
        intent.putExtra("note", note)
//        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE)
        startActivity(intent)
    }

//    private fun observeNotes(requestCode: Int) {
//        // 아니 씨발 viewmodel이 있고 LiveData로 notes를 저장하고 있는데 굳이 이렇게 원시적인 방법으로...
//        // startActivityForResult를 사용해서 REQUEST_CODE에 따라 SHOW, EDIT, ADD인지 구분한 다음에 결과에 따라서...
//        // 이렇게 하면 리스트를 두번 세번 호출함...
//        notesViewModel.notes.observe(this, Observer {
////            if (notes.size == 0) {
////            // We checked if the note list is empty it means the app is just started since we have declared it as a global variable, in this case, we are adding all notes from the database to this note list and notify the adapter about the new dataset.
////                notes.addAll(it)
////                notesAdapter.notifyDataSetChanged()
////            } else {
////            // In another case, if the note list is not empty then it means notes are already loaded from the database so we are just adding only the latest note to the note list and notify adapter about new note inserted. And last we scrolled our recycler view to the top
////                notes.add(0, it[0])
////                notesAdapter.notifyItemInserted(0)
////            }
////            activityMainBinding.notesRecyclerView.smoothScrollToPosition(0)
//
//            // 원래 로직
////            if (requestCode == REQUEST_CODE_SHOW_NOTES) {
////                noteList.addAll(notes);
////                notesAdapter.notifyDataSetChanged();
////            } else if (requestCode == REQUEST_CODE_ADD_NOTE) {
////                noteList.add(0, notes.get(0));
////                notesAdapter.notifyItemInserted(0);
////                notesRecyclerView.smoothScrollToPosition(0);
////            } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
////                noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
////                notesAdapter.notifyItemChanged(noteClickedPosition);
////            }
//
//            // 16개의 items에서 0번째 item을 수정했더니
//            // 0~16을 두번 순회하는 건 정확히 왜 이렇게 하는지 모르겠는데
//            // 어쨌든 리스트 결과는 중복해서 32를 보여줌…
//
//            Log.i(TAG, "requestCode : ${requestCode}")
//            notes.forEachIndexed { index, note ->
//                Log.i(TAG, "notes[$index]: ${note.title}")
//            }
//            it.forEach {
//                Log.i(TAG, "it: $it")
//            }
//
//            // 이렇게 하니까 정상 동작하긴 하는데...
//            if (notes.size == 0) {
//                // 아무 것도 없을때
//                notes.addAll(it)
//                notesAdapter.notifyDataSetChanged()
//            } else {
//                // 추가 또는 업데이트 되었을 때
//                if (requestCode == REQUEST_CODE_ADD_NOTE) {
//                    notes.clear()
//                    notes.add(0, it[0])
//                    notesAdapter.notifyItemInserted(0)
//                    activityMainBinding.notesRecyclerView.smoothScrollToPosition(0)
//                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
//                    notes.removeAt(noteClickedPosition)
//                    notes.add(noteClickedPosition, it[noteClickedPosition])
//                    notesAdapter.notifyItemChanged(noteClickedPosition)
//
//                    activityMainBinding.notesRecyclerView.smoothScrollToPosition(noteClickedPosition)
//                    // 수정한 노트로 이동해야 할꺼 아냐?
//                }
//            }
//
//        })
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
//            observeNotes(REQUEST_CODE_ADD_NOTE)
//        } else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
//            if (data != null) {
//                observeNotes(REQUEST_CODE_UPDATE_NOTE)
//            }
//        }
//    }
}