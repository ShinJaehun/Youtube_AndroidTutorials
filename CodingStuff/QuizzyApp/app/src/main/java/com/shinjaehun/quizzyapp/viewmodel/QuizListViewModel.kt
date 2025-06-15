package com.shinjaehun.quizzyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shinjaehun.quizzyapp.model.QuestionModel
import com.shinjaehun.quizzyapp.model.QuizListModel
import com.shinjaehun.quizzyapp.repository.QuizListRepository
import com.shinjaehun.quizzyapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    val repository: QuizListRepository
): ViewModel() {
    
    private val _quizLists = MutableLiveData<UiState<List<QuizListModel>>>()
    val quizLists : LiveData<UiState<List<QuizListModel>>> = _quizLists

    private val _questions = MutableLiveData<UiState<List<QuestionModel>>>()
    val questions : LiveData<UiState<List<QuestionModel>>> = _questions

    fun getQuizLists() {
        _quizLists.value = UiState.Loading
        repository.getQuizLists {
            _quizLists.value = it
        }
    }

    fun getQuestions(quizId: String) {
        _questions.value = UiState.Loading
        repository.getQuestions(quizId) {
            _questions.value = it
        }
    }
}