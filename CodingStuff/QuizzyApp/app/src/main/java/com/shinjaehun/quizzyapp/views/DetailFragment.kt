package com.shinjaehun.quizzyapp.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil3.load
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.databinding.FragmentDetailBinding
import com.shinjaehun.quizzyapp.model.QuizListModel
import com.shinjaehun.quizzyapp.util.hide
import com.shinjaehun.quizzyapp.util.loadImageFromDrawable
import com.shinjaehun.quizzyapp.util.loadImageFromUrl
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DetailFragment"

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    var quizList: QuizListModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            quizList = arguments?.getParcelable("quiz_list", QuizListModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            quizList = arguments?.getParcelable("quiz_list") as? QuizListModel
        }
//        Log.i(TAG, "title : ${quizList?.title}")
//        Log.i(TAG, "difficulty : ${quizList?.difficulty}")
//        Log.i(TAG, "questions : ${quizList?.questions}")

        binding.detailProgressBar.hide()

        if(quizList?.image?.isNotEmpty() == true){
            binding.detailFragmentImage.loadImageFromUrl(quizList!!.image)
        }else{
            binding.detailFragmentImage.loadImageFromDrawable(R.drawable.ic_no_image)
        }

        binding.detailFragmentTitle.text = quizList?.title
        binding.detailFragmentDifficulty.text = quizList?.difficulty
        binding.detailFragmentQuestions.text = quizList?.questions

        binding.startQuizBtn.setOnClickListener {
            Log.i(TAG, "id : ${quizList?.quizId}")

            findNavController().navigate(R.id.action_detailFragment_to_quizFragment, Bundle().apply {
                putString("quiz_id", quizList?.quizId)
            })
        }
    }
}