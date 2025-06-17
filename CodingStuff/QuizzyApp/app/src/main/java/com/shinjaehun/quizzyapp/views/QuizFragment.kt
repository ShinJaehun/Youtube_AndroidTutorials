package com.shinjaehun.quizzyapp.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.databinding.FragmentQuizBinding
import com.shinjaehun.quizzyapp.model.QuestionModel
import com.shinjaehun.quizzyapp.util.UiState
import com.shinjaehun.quizzyapp.util.hide
import com.shinjaehun.quizzyapp.util.show
import com.shinjaehun.quizzyapp.util.toast
import com.shinjaehun.quizzyapp.viewmodel.QuizListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

private const val TAG = "QuizFragment"

@AndroidEntryPoint
class QuizFragment : Fragment() {

    lateinit var binding: FragmentQuizBinding
    val quizListViewModel: QuizListViewModel by viewModels()

    var questions: List<QuestionModel> = arrayListOf()
    private lateinit var quizId: String
    private var currentQuestionNumber: Int = 0
    private var correctAnswer: Int = 0
    private var wrongAnswer: Int = 0
    var answer: String? = ""
    lateinit var job: Job



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizId = arguments?.getString("quiz_id")!!
        Log.i(TAG, "quizId: $quizId")
        observer()
        if (quizId!=null){
            quizListViewModel.getQuestions(quizId)
        }

        binding.option1Btn.setOnClickListener {
            binding.option2Btn.isClickable = false
            binding.option3Btn.isClickable = false
            verifyAnswer(it as Button)
        }
        binding.option2Btn.setOnClickListener {
            binding.option1Btn.isClickable = false
            binding.option3Btn.isClickable = false
            verifyAnswer(it as Button)
        }
        binding.option3Btn.setOnClickListener {
            binding.option1Btn.isClickable = false
            binding.option2Btn.isClickable = false
            verifyAnswer(it as Button)
        }

        binding.closeQuizBtn.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_listFragment)
        }
    }

    private fun observer() {
        quizListViewModel.questions.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    questions = state.data
                    Log.i(TAG, "questions: $questions")
                    loadQuestions(currentQuestionNumber)
                }
                else -> {
                    Log.i(TAG, "what??? : ${state}")
                }
            }
        }

        quizListViewModel.results.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Failure -> {
                    // 결과 upload 실패
                    toast(state.error)
                    Log.i(TAG, "헐 업로드 실패 : ${state.error}")
                }
                is UiState.Success -> {
                    // 여기서 navigation으로 넘어가야 함
                    Log.i(TAG, "일단 업로드 성공 : ${state.data.second}")
                    findNavController().navigate(R.id.action_quizFragment_to_resultFragment, Bundle().apply {
                        putString("correct_answer", correctAnswer.toString())
                        putString("wrong_answer", wrongAnswer.toString())
                    })
                }
                else -> {
                    Log.i(TAG, "what??? : ${state}")
                }
            }
        }
    }

    private fun loadQuestions(i: Int) {
        Log.i(TAG, "currentQuestionNumber $i")

        resetOptions()

        val currentQuestion = questions[i]
        Log.i(TAG, "currentQuestion: $currentQuestion")
        binding.quizQuestionTv.text = currentQuestion.question
        binding.option1Btn.text = currentQuestion.option_a
        binding.option2Btn.text = currentQuestion.option_b
        binding.option3Btn.text = currentQuestion.option_c
        binding.countTimeQuiz.text = currentQuestion.timer.toString()
        binding.quizQuestionsCount.text = "${i+1} / ${questions.size}"

        answer = currentQuestion.answer

        binding.quizCoutProgressBar.show()
        job = lifecycleScope.launch {
            startTimer(currentQuestion.timer).collect { remaining ->
                binding.countTimeQuiz.text = remaining.toString()
                val percent = remaining.toDouble() / currentQuestion.timer.toDouble() * 100
//                Log.i(TAG, "remaining : $remaining")
//                Log.i(TAG, "currentQuestion.timer : ${currentQuestion.timer}")
//                Log.i(TAG, "percent : $percent")
                binding.quizCoutProgressBar.setProgress(percent.toInt())
            }
            wrongAnswer++

            binding.option1Btn.isClickable = false
            binding.option2Btn.isClickable = false
            binding.option3Btn.isClickable = false

            binding.ansFeedbackTv.text = "Times Up! No answer selected..."
            clearAll()
        }
    }

    private fun resetOptions() {
        binding.option1Btn.isClickable = true
        binding.option2Btn.isClickable = true
        binding.option3Btn.isClickable = true

        binding.option1Btn.setBackgroundResource(R.drawable.button_bg)
        binding.option2Btn.setBackgroundResource(R.drawable.button_bg)
        binding.option3Btn.setBackgroundResource(R.drawable.button_bg)

        binding.nextQueBtn.hide()
    }

    private fun startTimer(durationSeconds: Int): Flow<Int> = flow {
        var remainingSeconds = durationSeconds
        while(remainingSeconds > 0){
            emit(remainingSeconds)
            delay(1000)
            remainingSeconds--
        }
        emit(0)
    }

    private fun verifyAnswer(button: Button) {
        if(answer!!.equals(button.text)) {
//            button.setBackground(ContextCompat.getDrawable(context, R.color.green))
            correctAnswer++
            button.setBackgroundResource(R.drawable.button_bg_correct)
            binding.ansFeedbackTv.text = "Correct Answer"
        } else {
            wrongAnswer++
            button.setBackgroundResource(R.drawable.button_bg_wrong)
            binding.ansFeedbackTv.text = "Wrong Answer"
        }

        clearAll()

    }

    private fun clearAll() {
        job.cancel()
        binding.nextQueBtn.show()
        currentQuestionNumber++
        binding.nextQueBtn.setOnClickListener {
            if(currentQuestionNumber == questions.size) {
                toast("quiz has been finished!")
                submitResults()
            } else {
                loadQuestions(currentQuestionNumber)
            }
        }
    }

    private fun submitResults() {
        val resultMap = HashMap<String, Int>()
        resultMap.put("correct", correctAnswer)
        resultMap.put("wrong", wrongAnswer)

        quizListViewModel.putResults(quizId, resultMap) //근데 이 결과를 FB에 저장하는 게 무슨 의미가 있는지 잘 모르겠음...
    }
}