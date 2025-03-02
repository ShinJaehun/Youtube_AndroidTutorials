package com.shinjaehun.mvvmnotefirebase.note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.shinjaehun.mvvmnotefirebase.R
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.databinding.FragmentNoteListingBinding
import com.shinjaehun.mvvmnotefirebase.util.UiState
import com.shinjaehun.mvvmnotefirebase.util.hide
import com.shinjaehun.mvvmnotefirebase.util.show
import com.shinjaehun.mvvmnotefirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "NoteListingFragment"

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    private lateinit var binding: FragmentNoteListingBinding
    val viewModel : NoteViewModel by viewModels()

    var deletePosition: Int = -1
    var list: MutableList<Note> = arrayListOf()

    val adapter by lazy {
        NoteListingAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment, Bundle().apply {
//                    putString("type", "view")
                    putParcelable("note", item)
                })
            },
//            onEditClicked = { pos, item ->
//                findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment, Bundle().apply {
//                    putString("type", "edit")
//                    putParcelable("note", item)
//                })
//            },
//            onDeleteClicked = { pos, item ->
//                deletePosition = pos
//                viewModel.deleteNote(item)
//            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteListingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.itemAnimator = null // delete note bug fix

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment, Bundle().apply {
                putString("type", "create")
            })
        }
        viewModel.getNotes()
        viewModel.notes.observe(viewLifecycleOwner) {state ->
//            it.forEach {
//                Log.i(TAG, it.toString())
//            }
            when(state) {
                is UiState.Loading -> {
//                    Log.i(TAG, "Loading...")
//                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
//                    Log.i(TAG, state.error.toString())
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
//                    state.data.forEach {
//                        Log.i(TAG, it.toString())
//                    }
                    binding.progressBar.hide()
                    list = state.data.toMutableList()
//                    adapter.updateList(state.data.toMutableList())
                    adapter.updateList(list)
                }
            }
        }

        // no more deleting in note listing fragment
//        viewModel.deleteNote.observe(viewLifecycleOwner) {state ->
//            when(state) {
//                is UiState.Loading -> {
//                    binding.progressBar.show()
//                }
//                is UiState.Failure -> {
//                    binding.progressBar.hide()
//                    toast(state.error)
//                }
//                is UiState.Success -> {
//                    binding.progressBar.hide()
////                    if (deletePosition != -1) {
////                        list.removeAt(deletePosition)
////                        adapter.updateList(list)
////                    }
//                    toast(state.data)
//                    adapter.removeItem(deletePosition)
//                }
//            }
//        }
    }
}