package com.shinjaehun.mvvmnotefirebase.ui.task

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shinjaehun.mvvmnotefirebase.R
import com.shinjaehun.mvvmnotefirebase.data.model.Task
import com.shinjaehun.mvvmnotefirebase.databinding.FragmentCreateTaskBinding
import com.shinjaehun.mvvmnotefirebase.ui.auth.AuthViewModel
import com.shinjaehun.mvvmnotefirebase.util.UiState
import com.shinjaehun.mvvmnotefirebase.util.hide
import com.shinjaehun.mvvmnotefirebase.util.show
import com.shinjaehun.mvvmnotefirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "CreateTaskFragment"

@AndroidEntryPoint
class CreateTaskFragment(
    private val task: Task? = null
): BottomSheetDialogFragment() {

    lateinit var binding: FragmentCreateTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private var closeFunction: ((Boolean) -> Unit)? = null
    private var isSuccessAddTask: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        task?.let {
            binding.taskEt.setText(it.description)
        }

        binding.cancel.setOnClickListener {
            this.dismiss()
        }

        binding.done.setOnClickListener {
            if (validation()) {
                if (task == null) {
                    taskViewModel.addTask(getTask())
                } else {
                    task.description = binding.taskEt.text.toString()
                    taskViewModel.updateTask(task)
                }
            }
        }
        observer()
    }

    private fun observer() {
        taskViewModel.addTask.observe(viewLifecycleOwner) { state ->
            when(state) {
                UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    isSuccessAddTask = true
                    binding.progressBar.hide()
                    toast(state.data.second)
                    this.dismiss()
                }
            }
        }

        taskViewModel.updateTask.observe(viewLifecycleOwner) { state ->
            when(state) {
                UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    isSuccessAddTask = true
                    binding.progressBar.hide()
                    toast(state.data.second)
                    this.dismiss()
                }
            }
        }

    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.taskEt.text.toString().isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.error_task_detail))
        }
        return isValid
    }

    private fun getTask(): Task {
        val sdf = SimpleDateFormat("yyyy MMM dd . hh:mm a")
        return Task(
            id = "",
            description = binding.taskEt.text.toString(),
            date = sdf.format(Date())
        ).apply { authViewModel.getSession { this.user_id = it?.id ?: "" }}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        closeFunction?.invoke(isSuccessAddTask)
    }

    fun setDismissListener(function: ((Boolean) -> Unit)?){
        closeFunction = function
    }

}