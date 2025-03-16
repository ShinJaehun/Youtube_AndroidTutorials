package com.shinjaehun.mvvmnotefirebase.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shinjaehun.mvvmnotefirebase.data.model.Task
import com.shinjaehun.mvvmnotefirebase.databinding.FragmentTaskListingBinding
import com.shinjaehun.mvvmnotefirebase.ui.auth.AuthViewModel
import com.shinjaehun.mvvmnotefirebase.ui.note.NoteListingFragment
import com.shinjaehun.mvvmnotefirebase.util.UiState
import com.shinjaehun.mvvmnotefirebase.util.hide
import com.shinjaehun.mvvmnotefirebase.util.show
import com.shinjaehun.mvvmnotefirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TaskListingFragment"
private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class TaskListingFragment : Fragment() {
    private var param1: String? = null
    lateinit var binding: FragmentTaskListingBinding

    val taskViewModel: TaskViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()

    var deleteItemPos = -1

    val adapter by lazy {
        TaskListingAdapter(
            onItemClicked = { pos, item -> onTaskClicked(item) },
            onDeletedClicked = { pos, item -> onDeleteClicked(pos, item) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentTaskListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTaskButton.setOnClickListener {
            val createTaskFragmentSheet = CreateTaskFragment()
            // Sheet이 사라지면서 tasks를 갱신해야 함
            createTaskFragmentSheet.setDismissListener {
                if (it) {
                    authViewModel.getSession {
                        taskViewModel.getTasks(it)
                    }
                }
            }
            createTaskFragmentSheet.show(childFragmentManager, "create_task")
        }

        binding.taskListing.layoutManager = LinearLayoutManager(requireContext())
        binding.taskListing.adapter = adapter

        binding.taskListing.itemAnimator = null // delete task bug fix

        authViewModel.getSession {
            taskViewModel.getTasks(it)
        }

        observer()

    }

    private fun observer() {
        taskViewModel.tasks.observe(viewLifecycleOwner) {state ->
            when(state) {
                UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    Log.i(TAG, state.error)
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }

        taskViewModel.deleteTask.observe(viewLifecycleOwner) { state ->
            when(state) {
                UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.show()
                    toast(state.data.second)
                    Log.i(TAG, "delete item position: $deleteItemPos")
                    if (deleteItemPos != -1) {
//                        adapter.removeItem(deleteItemPos) // 이렇게 지우면 이상한 결과...
                        authViewModel.getSession { // 오히려 tasks를 갱신하는 게 낫지!
                            taskViewModel.getTasks(it)
                        }
                    }
                }
            }
        }
    }

    private fun onTaskClicked(task: Task) {
        val createTaskFragmentSheet = CreateTaskFragment(task)
        createTaskFragmentSheet.setDismissListener {
            if (it) {
                authViewModel.getSession {
                    taskViewModel.getTasks(it)
                }
            }
        }
        createTaskFragmentSheet.show(childFragmentManager, "create_task")
    }

    private fun onDeleteClicked(pos: Int, task: Task) {
        deleteItemPos = pos
        taskViewModel.deleteTask(task)
    }

    companion object {
        fun newInstance(param1: String) =
            TaskListingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}