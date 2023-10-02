package com.shinjaehun.quizapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shinjaehun.quizapp.Constants
import com.shinjaehun.quizapp.Question

private const val TAG = "QuizQuestionsViewModel"

class QuizQuestionsViewModel: ViewModel() {
    private var questionsList: MutableList<Question> = mutableListOf()

    private val _currentPosition = MutableLiveData(1)
    val currentPosition: LiveData<Int>
        get() = _currentPosition

    private val _correctAnswers = MutableLiveData(0)
    val correctAnswers: LiveData<Int>
        get() = _correctAnswers

    private var _selectedOptionPosition = MutableLiveData(0)
    val selectedOptionPosition: LiveData<Int>
        get() = _selectedOptionPosition

    init {
        Log.i(TAG, "is woriking?")
        questionsList = Constants.getQuestions()
    }

    fun userInput(selectedOptionNumber: Int) {
        _selectedOptionPosition.value = selectedOptionNumber
    }

    fun getQuestion() : Question {
        return questionsList[_currentPosition.value!! - 1]
    }

    fun nextQuestion() {
        _currentPosition.value = _currentPosition.value!! + 1
    }

    fun getTotalQuestions() : Int {
        return questionsList.size
    }

    fun isUserCorrect() : Boolean {
        Log.i(TAG, "selectedOptionNumber: ${_selectedOptionPosition.value}, correctAnswer: ${getQuestion().correctAnswer}")
        return if (_selectedOptionPosition.value == getQuestion().correctAnswer) {
            true
        } else false
    }

    fun increaseCorrectAnswer() {
        _correctAnswers.value = _correctAnswers.value!! + 1
    }

    fun isGameContinue() : Boolean {
        return _currentPosition.value!! <= getTotalQuestions()
    }

    fun isGameFinish() : Boolean {
        return _currentPosition.value!! == getTotalQuestions()
    }
}