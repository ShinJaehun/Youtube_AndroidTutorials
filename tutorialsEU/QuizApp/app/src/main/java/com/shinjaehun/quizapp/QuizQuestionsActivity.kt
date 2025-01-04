package com.shinjaehun.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView

import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.shinjaehun.quizapp.databinding.ActivityQuizQuestionsBinding
import com.shinjaehun.quizapp.viewmodels.QuizQuestionsViewModel

class QuizQuestionsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding
    private lateinit var viewModel: QuizQuestionsViewModel

//    private var mCurrentPosition: Int = 1
//    private var mQuestionsList: ArrayList<Question>? = null
//    private var mSelectedOptionPosition : Int = 0
//    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null

    companion object {
        private const val TAG = "QuizQuestionsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN // deprecated

        viewModel = QuizQuestionsViewModel()

//        viewModel = QuizQuestionsViewModel()

//        val questionsList = Constants.getQuestions()
//        Log.i(TAG, "${questionsList.size}")

        mUserName = intent.getStringExtra(Constants.USER_NAME)

//        mQuestionsList = Constants.getQuestions()
        setQuestion()

//        val currentPosition = 1
//        val question: Question? = questionsList[currentPosition - 1]

//        binding.progressBar.progress = currentPosition
//        binding.tvProgress.text = "$currentPosition / ${binding.progressBar.max}"
//        binding.tvQuestion.text = question!!.question
//        binding.ivImage.setImageResource(question.image)
//        binding.tvOptionOne.text = question.optionOne
//        binding.tvOptionTwo.text = question.optionTwo
//        binding.tvOptionThree.text = question.optionThree
//        binding.tvOptionFour.text = question.optionFour

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
//        mCurrentPosition = 1
//        val question = mQuestionsList!![mCurrentPosition - 1]

        val question = viewModel.getQuestion()

        defaultOptionsView()

        if (viewModel.isGameFinish()) {
            binding.btnSubmit.text = "Finish"
        } else {
            binding.btnSubmit.text = "Submit"
        }

        binding.progressBar.progress = viewModel.currentPosition.value!!
        binding.progressBar.max = viewModel.getTotalQuestions()
        binding.tvProgress.text = "${viewModel.currentPosition.value}/${binding.progressBar.max}"
        binding.tvQuestion.text = question.question
        binding.ivImage.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        options.forEach {
            it.setTextColor(Color.parseColor("#7a8089"))
            it.typeface = Typeface.DEFAULT
            it.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                viewModel.userInput(1)
                selectedOptionView(binding.tvOptionOne)
            }
            R.id.tv_option_two -> {
                viewModel.userInput(2)
                selectedOptionView(binding.tvOptionTwo)
            }
            R.id.tv_option_three -> {
                viewModel.userInput(3)
                selectedOptionView(binding.tvOptionThree)
            }
            R.id.tv_option_four -> {
                viewModel.userInput(4)
                selectedOptionView(binding.tvOptionFour)
            }
            R.id.btn_submit -> {
                if (viewModel.selectedOptionPosition.value == 0) {
                    viewModel.nextQuestion() // 다른 문제로 넘어간다는겨?
                    when {
                        viewModel.isGameContinue() -> {
                            setQuestion()
                        }
                        else -> {
//                            Toast.makeText(
//                                this,
//                                "You have successfully completed the quiz",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, viewModel.correctAnswers.value)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, viewModel.getTotalQuestions())
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
//                    Log.i(TAG, "selectedOptionNumber: $selectedOptionNumber")
                    if (!viewModel.isUserCorrect()) {
//                        Log.i(TAG, "selectedOptionPosition: ${viewModel.selectedOptionPosition.value!!}")
                        answerView(viewModel.selectedOptionPosition.value!!, R.drawable.wrong_option_border_bg)
                    } else {
                        viewModel.increaseCorrectAnswer()
                    }
                    answerView(viewModel.getQuestion().correctAnswer, R.drawable.correct_option_border_bg)
                    if(viewModel.isGameFinish()) {
                        binding.btnSubmit.text = "Finish"
                    } else {
                        binding.btnSubmit.text = "Go to next question"
                    }

                    viewModel.userInput(0)

//                    val question = mQuestionsList?.get(mCurrentPosition - 1)
//                    if (question!!.correctAnswer != mSelectedOptionPosition) {
//                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
//                    } else {
//                        mCorrectAnswers++
//                    }
//                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
//                    if (mCurrentPosition == mQuestionsList!!.size) {
//                        binding.btnSubmit.text = "Finish"
//                    } else {
//                        binding.btnSubmit.text = "Go to next question"
//                    }
//                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                binding.tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                binding.tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                binding.tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv: TextView) {
        defaultOptionsView()

        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}