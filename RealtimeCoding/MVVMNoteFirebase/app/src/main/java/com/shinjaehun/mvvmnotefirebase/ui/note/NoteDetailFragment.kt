package com.shinjaehun.mvvmnotefirebase.ui.note

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.shinjaehun.mvvmnotefirebase.R
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.databinding.FragmentNoteDetailBinding
import com.shinjaehun.mvvmnotefirebase.util.UiState
import com.shinjaehun.mvvmnotefirebase.util.addChip
import com.shinjaehun.mvvmnotefirebase.util.createDialog
import com.shinjaehun.mvvmnotefirebase.util.dpToPx
import com.shinjaehun.mvvmnotefirebase.util.hide
import com.shinjaehun.mvvmnotefirebase.util.show
import com.shinjaehun.mvvmnotefirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "NoteDetailFragment"

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    val viewModel : NoteViewModel by viewModels()
//    var isEdit = false
    var objNote: Note? = null
    var tagsList: MutableList<String> = arrayListOf()

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
        observer()
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

//        binding.button.setOnClickListener {
//            if(isEdit){
//                updateNote()
//            } else {
//                createNote()
//            }
//        }
    }

    private fun observer() {
        viewModel.addNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data.second)
                    objNote = state.data.first
                    isMakeEnableUI(false)
                    binding.done.hide()
                    binding.delete.show()
                    binding.edit.show()
                }
            }
        }

        viewModel.updateNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                    isMakeEnableUI(false)
                    binding.done.hide()
                    binding.edit.show()
                }
            }
        }

        viewModel.deleteNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> { // 아직 삭제 안하는 거지?
                    binding.progressBar.hide()
                    toast(state.data)
                    findNavController().navigateUp()
                }
            }
        }
    }

//    private fun createNote(){
//        if(validataion()) {
//            viewModel.addNote(
//                Note(
//                    id = "",
//                    text = binding.noteMsg.text.toString(),
//                    date = Date(),
//                )
//            )
//        }
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
//    }
//
//    private fun updateNote() {
//        if(validataion()) {
//            viewModel.updateNote(
//                Note(
//                    id = objNote?.id ?: "",
//                    text = binding.noteMsg.text.toString(),
//                    date = Date(),
//                )
//            )
//        }
//        viewModel.updateNote.observe(viewLifecycleOwner) { state ->
//            when(state) {
//                is UiState.Loading -> {
//                    binding.btnProgressAr.show()
//                    binding.button.text = ""
//                }
//
//                is UiState.Failure -> {
//                    binding.btnProgressAr.hide()
//                    binding.button.text = "Update"
//                    toast(state.error)
//                }
//
//                is UiState.Success -> {
//                    binding.btnProgressAr.hide()
////                    toast("Note has been created successfully")
//                    binding.button.text = "Update"
//                    toast(state.data)
//                }
//            }
//        }
//    }

//    private fun updateUI() {
//        val type = arguments?.getString("type", null)
//        type?.let { type ->
//            when(type) {
//                "view" -> {
//                    isEdit = false
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        objNote = arguments?.getParcelable("note", Note::class.java)
//                    } else {
//                        @Suppress("DEPRECATION")
//                        objNote = arguments?.getParcelable("note") as? Note
//                    }
////                    binding.noteMsg.isEnabled = false
//                    binding.noteMsg.setText(objNote?.text)
//                    binding.button.hide()
//                }
//                "create" -> {
//                    isEdit = false
//                    binding.button.setText("Create")
//                }
//                "edit" -> {
//                    isEdit = true
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        objNote = arguments?.getParcelable("note", Note::class.java)
//                    } else {
//                        @Suppress("DEPRECATION")
//                        objNote = arguments?.getParcelable("note") as? Note
//                    }
//                    binding.noteMsg.setText(objNote?.text)
//                    binding.button.setText("Update")
//                }
//            }
//        }
//    }

    private fun updateUI(){
        val sdf = SimpleDateFormat("yyyy MMM dd . hh:mm a")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            objNote = arguments?.getParcelable("note", Note::class.java)
        } else {
            @Suppress("DEPRECATION")
            objNote = arguments?.getParcelable("note") as? Note
        }

        binding.tags.layoutParams.height = 40.dpToPx

        objNote?.let { note ->
            binding.title.setText(note.title)
            binding.date.setText(sdf.format(note.date))
            tagsList = note.tags
            addTags(tagsList)
            binding.description.setText(note.description)
            binding.done.hide()
            binding.edit.show()
            binding.delete.show()
            isMakeEnableUI(false)
        } ?: run {
            binding.title.setText("")
            binding.date.setText(sdf.format(Date()))
            binding.description.setText("")
            binding.done.hide()
            binding.edit.hide()
            binding.delete.hide()
            isMakeEnableUI(true)
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.title.setOnClickListener {
            isMakeEnableUI(true)
        }

        binding.description.setOnClickListener {
            isMakeEnableUI(true)
        }

        binding.delete.setOnClickListener {
            objNote?.let { viewModel.deleteNote(it) }
        }

        binding.addTagLl.setOnClickListener {
            showAddTagDialog()
        }

        binding.edit.setOnClickListener {
            isMakeEnableUI(true)
            binding.done.show()
            binding.edit.hide()
            binding.title.requestFocus()
        }

        binding.done.setOnClickListener {
            if (validataion()) {
                if (objNote == null) {
                    viewModel.addNote(getNote())
                } else {
                    viewModel.updateNote(getNote())
                }
            }
        }

        binding.title.doAfterTextChanged {
            binding.done.show()
            binding.edit.hide()
        }

        binding.description.doAfterTextChanged {
            binding.done.show()
            binding.edit.hide()
        }
    }

    private fun showAddTagDialog() {
        val dialog = requireContext().createDialog(R.layout.add_tag_dialog, true)
        val button = dialog.findViewById<MaterialButton>(R.id.tag_dialog_add)
        val editText = dialog.findViewById<EditText>(R.id.tag_dialog_et)
        button.setOnClickListener {
            if(editText.text.toString().isNullOrEmpty()) {
                toast(getString(R.string.error_tag_text))
            } else {
                val text = editText.text.toString()
                tagsList.add(text)
                binding.tags.apply {
                    addChip(text, true) {
                        tagsList.forEachIndexed { index, tag ->
                            if (text.equals(tag)) {
                                tagsList.removeAt(index)
                                binding.tags.removeViewAt(index)
                            }
                        }
                        if(tagsList.size == 0) {
                            layoutParams.height = 40.dpToPx
                        }
                    }
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                binding.done.show()
                binding.edit.hide()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun addTags(note: MutableList<String>) {
        if(note.size > 0) {
            binding.tags.apply {
                removeAllViews()
                note.forEachIndexed { index, tag ->
                    addChip(tag, true) {
                        if (isEnabled) {
                            note.removeAt(index)
                            this.removeViewAt(index)
                        }
                    }
                }
            }
        }
    }

    private fun isMakeEnableUI(isDisable: Boolean = false) {
        binding.title.isEnabled = isDisable
        binding.date.isEnabled = isDisable
        binding.tags.isEnabled = isDisable
        binding.addTagLl.isEnabled = isDisable
        binding.description.isEnabled = isDisable
    }

//    private fun validataion(): Boolean {
//        var isValid = true
//
//        if(binding.noteMsg.text.toString().isNullOrEmpty()) {
//            isValid = false
//            toast("Enter message")
//        }
//        return isValid
//    }

    private fun validataion(): Boolean {
        var isValid = true

        if(binding.title.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_title))
        }

        if(binding.description.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_description))
        }

        return isValid
    }

    private fun getNote(): Note {
        return Note(
            id = objNote?.id ?: "",
            title = binding.title.text.toString(),
            description = binding.description.text.toString(),
            tags = tagsList,
            date = Date()
        )
    }
}