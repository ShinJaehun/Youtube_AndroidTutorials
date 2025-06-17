package com.shinjaehun.quizzyapp.repository

import com.shinjaehun.quizzyapp.model.QuestionModel
import com.shinjaehun.quizzyapp.model.QuizListModel
import com.shinjaehun.quizzyapp.util.UiState

interface QuizListRepository {
    fun getQuizLists(result: (UiState<List<QuizListModel>>) -> Unit)
    fun getQuestions(quizId: String, result: (UiState<List<QuestionModel>>) -> Unit)
    fun putResults(quizId: String,
                   resultMap: HashMap<String, Int>,
                   result: (UiState<Pair<HashMap<String, Int>, String>>) -> Unit)
}