package com.shinjaehun.quizzyapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.adapter.QuizListAdapter
import com.shinjaehun.quizzyapp.databinding.FragmentListBinding
import com.shinjaehun.quizzyapp.util.UiState
import com.shinjaehun.quizzyapp.util.hide
import com.shinjaehun.quizzyapp.util.show
import com.shinjaehun.quizzyapp.util.toast
import com.shinjaehun.quizzyapp.viewmodel.QuizListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    val quizListViewModel: QuizListViewModel by viewModels()
    val adapter by lazy {
        QuizListAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, Bundle().apply {
                    putParcelable("quiz_list", item)
                })
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listQuizRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.listQuizRecyclerview.adapter = adapter
        observer()

        quizListViewModel.getQuizLists()
    }

    private fun observer() {
        quizListViewModel.quizLists.observe(viewLifecycleOwner) {state ->
            when(state){
                UiState.Loading -> binding.quizListProgressbar.show()
                is UiState.Failure -> {
                    binding.quizListProgressbar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.quizListProgressbar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }

    }

}