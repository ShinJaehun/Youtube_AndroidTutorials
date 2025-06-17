package com.shinjaehun.quizzyapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val correctAnswer = arguments?.getString("correct_answer")
        val wrongAnswer = arguments?.getString("wrong_answer")

        binding.correctAnswerTv.text = correctAnswer
        binding.wrongAnswersTv.text = wrongAnswer

        binding.homeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_listFragment)
        }
    }

}