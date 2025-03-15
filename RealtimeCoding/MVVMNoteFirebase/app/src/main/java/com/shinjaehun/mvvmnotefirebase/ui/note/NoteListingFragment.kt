package com.shinjaehun.mvvmnotefirebase.ui.note

import android.os.Bundle
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
import com.shinjaehun.mvvmnotefirebase.ui.auth.AuthViewModel
import com.shinjaehun.mvvmnotefirebase.util.UiState
import com.shinjaehun.mvvmnotefirebase.util.hide
import com.shinjaehun.mvvmnotefirebase.util.show
import com.shinjaehun.mvvmnotefirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "NoteListingFragment"
private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    var param1: String? = null
    private lateinit var binding: FragmentNoteListingBinding
    val noteViewModel : NoteViewModel by viewModels()
    val authViewModel : AuthViewModel by viewModels()

    val adapter by lazy {
        NoteListingAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment, Bundle().apply {
                    putParcelable("note", item)
                })
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentNoteListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter

//        binding.recyclerView.itemAnimator = null // delete note bug fix

        binding.button.setOnClickListener {
//            findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment, Bundle().apply {
//                putString("type", "create")
//            })
            findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment)
        }

//        binding.logout.setOnClickListener {
//            authViewModel.logout {
//                findNavController().navigate(R.id.action_noteListingFragment_to_loginFragment)
//            }
//        }

        authViewModel.getSession {
            noteViewModel.getNotes(it)
        }
    }

    private fun observer() {
        noteViewModel.notes.observe(viewLifecycleOwner) { state ->

            when(state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }

    companion object {
        fun newInstance(param1: String) =
            NoteListingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}