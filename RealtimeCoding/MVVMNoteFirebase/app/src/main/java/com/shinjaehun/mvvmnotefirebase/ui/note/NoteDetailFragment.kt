package com.shinjaehun.mvvmnotefirebase.ui.note

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.shinjaehun.mvvmnotefirebase.R
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.databinding.FragmentNoteDetailBinding
import com.shinjaehun.mvvmnotefirebase.ui.auth.AuthViewModel
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
    val noteViewModel : NoteViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    var objNote: Note? = null
    var tagsList: MutableList<String> = arrayListOf()
    var imageUris: MutableList<Uri> = arrayListOf()

    val adapter by lazy {
        ImageListingAdapter(
            onCancelClicked = { pos -> onRemoveImage(pos)}
        )
    }

    private val pickMedia =
        registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.progressBar.hide()
                imageUris.add(uri)
                adapter.updateList(imageUris)
            } else {
                binding.progressBar.hide()
                toast("No Media selected!")
            }
    }

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
    }

    private fun observer() {
        noteViewModel.addNote.observe(viewLifecycleOwner) { state ->
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

        noteViewModel.updateNote.observe(viewLifecycleOwner) { state ->
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

        noteViewModel.deleteNote.observe(viewLifecycleOwner) { state ->
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

        binding.images.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.images.adapter = adapter
        binding.images.itemAnimator = null

        imageUris = objNote?.images?.map { it.toUri() }?.toMutableList() ?: arrayListOf()
        adapter.updateList(imageUris)

        binding.addImageLl.setOnClickListener {
            binding.progressBar.show()
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
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
            objNote?.let { noteViewModel.deleteNote(it) }
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
//            if (validataion()) {
//                if (objNote == null) {
//                    noteViewModel.addNote(getNote())
//                } else {
//                    noteViewModel.updateNote(getNote())
//                }
//            }
            if (validataion()) {
                onDonePressed()
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

    private fun onRemoveImage(pos: Int) {
        adapter.removeItem(pos)
        if (objNote != null) {
            binding.edit.performClick()
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
            images = getImageUrls(),
            date = Date()
        ).apply { authViewModel.getSession { this.user_id = it?.id ?: "" } }
    }

    private fun getImageUrls(): List<String> {
        if (imageUris.isNotEmpty()) {
            // url과 content uri가 혼재되어 있기 때문에
            // url만 저장할 수 있게...
            val filteredUris = imageUris.filterNot { it.scheme == "content" }.map { it.toString() }
            Log.i(TAG, "filteredUris: $filteredUris")
            return filteredUris
        } else {
            return objNote?.images ?: arrayListOf()
        }
    }

    private fun onDonePressed() {
        if (imageUris.isNotEmpty()) {
//            noteViewModel.onUploadSingleFile(imageUris.first()) { state ->
            noteViewModel.onUploadMultipleFiles(imageUris) { state ->
                when (state) {
                    UiState.Loading -> {
                        binding.progressBar.show()
                    }
                    is UiState.Failure -> {
                        binding.progressBar.hide()
                        toast(state.error)
                    }
                    is UiState.Success -> {
                        Log.i(TAG, "uri from FB: ${state.data}")
                        Log.i(TAG, "before imageUris: $imageUris")
                        if (!state.data.isNullOrEmpty()) {
                            // FB가 반환해준 업로드한 파일 url
                            state.data.map {
                                imageUris.add(it)
                            }
                        }

                        binding.progressBar.hide()
                        if (objNote == null) {
                            noteViewModel.addNote(getNote())
                        } else {
                            noteViewModel.updateNote(getNote())
                        }
                    }
                }
            }
        } else {
            if (objNote == null) {
                noteViewModel.addNote(getNote())
            } else {
                noteViewModel.updateNote(getNote())
            }
        }
    }
}