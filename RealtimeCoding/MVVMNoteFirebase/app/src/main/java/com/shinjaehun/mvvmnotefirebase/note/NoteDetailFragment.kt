package com.shinjaehun.mvvmnotefirebase.note

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.shinjaehun.mvvmnotefirebase.R
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.databinding.FragmentNoteDetailBinding
import com.shinjaehun.mvvmnotefirebase.util.UiState
import com.shinjaehun.mvvmnotefirebase.util.hide
import com.shinjaehun.mvvmnotefirebase.util.show
import com.shinjaehun.mvvmnotefirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

private const val TAG = "NoteDetailFragment"

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    val viewModel : NoteViewModel by viewModels()
    var isEdit = false
    var objNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()

//        binding.button.setOnClickListener {
//            if(validataion()) {
//                viewModel.addNote(
//                    Note(
//                        id = "",
//                        text = binding.noteMsg.text.toString(),
//                        date = Date(),
//                    )
//                )
//            }
//        }
//
//        viewModel.addNote.observe(viewLifecycleOwner) { state ->
//            when(state) {
//                is UiState.Loading -> {
//                    binding.btnProgressAr.show()
//                    binding.button.text = ""
//                }
//
//                is UiState.Failure -> {
//                    binding.btnProgressAr.hide()
//                    binding.button.text = "Create"
//                    toast(state.error)
//                }
//
//                is UiState.Success -> {
//                    binding.btnProgressAr.hide()
////                    toast("Note has been created successfully")
//                    binding.button.text = "Create"
//                    toast(state.data)
//                }
//            }
//        }

        binding.button.setOnClickListener {
            if(isEdit){
                updateNote()
            } else {
                createNote()
            }
        }
    }

    private fun createNote(){
        if(validataion()) {
            viewModel.addNote(
                Note(
                    id = "",
                    text = binding.noteMsg.text.toString(),
                    date = Date(),
                )
            )
        }
        viewModel.addNote.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    binding.btnProgressAr.show()
                    binding.button.text = ""
                }

                is UiState.Failure -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Create"
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.btnProgressAr.hide()
//                    toast("Note has been created successfully")
                    binding.button.text = "Create"
                    toast(state.data)
                }
            }
        }
    }

    private fun updateNote() {
        if(validataion()) {
            viewModel.updateNote(
                Note(
                    id = objNote?.id ?: "",
                    text = binding.noteMsg.text.toString(),
                    date = Date(),
                )
            )
        }
        viewModel.updateNote.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Loading -> {
                    binding.btnProgressAr.show()
                    binding.button.text = ""
                }

                is UiState.Failure -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Update"
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.btnProgressAr.hide()
//                    toast("Note has been created successfully")
                    binding.button.text = "Update"
                    toast(state.data)
                }
            }
        }
    }

    private fun updateUI() {
        val type = arguments?.getString("type", null)
        type?.let { type ->
            when(type) {
                "view" -> {
                    isEdit = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        objNote = arguments?.getParcelable("note", Note::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        objNote = arguments?.getParcelable("note") as? Note
                    }
//                    binding.noteMsg.isEnabled = false
                    binding.noteMsg.setText(objNote?.text)
                    binding.button.hide()
                }
                "create" -> {
                    isEdit = false
                    binding.button.setText("Create")
                }
                "edit" -> {
                    isEdit = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        objNote = arguments?.getParcelable("note", Note::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        objNote = arguments?.getParcelable("note") as? Note
                    }
                    binding.noteMsg.setText(objNote?.text)
                    binding.button.setText("Update")
                }
            }

        }
    }

    private fun validataion(): Boolean {
        var isValid = true

        if(binding.noteMsg.text.toString().isNullOrEmpty()) {
            isValid = false
            toast("Enter message")
        }
        return isValid
    }

}