package com.shinjaehun.quizzyapp.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.shinjaehun.quizzyapp.model.QuestionModel
import com.shinjaehun.quizzyapp.model.QuizListModel
import com.shinjaehun.quizzyapp.util.UiState

private const val TAG = "QuizListRepositoryImpl"

class QuizListRepositoryImpl(
    private val firestore: FirebaseFirestore
): QuizListRepository {
    override fun getQuizLists(result: (UiState<List<QuizListModel>>) -> Unit) {
        firestore.collection("quizzyapp_quiz")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val quizLists = arrayListOf<QuizListModel>()
                    for (document in it.result) {
                        Log.i(TAG, "$document")
                        val quiz = document.toObject(QuizListModel::class.java)
                        quiz.quizId = document.id
                        quizLists.add(quiz)
                    }
                    result.invoke(UiState.Success(quizLists))
                }else{
                    result.invoke(UiState.Failure(it.exception?.message.toString()))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.message.toString()))
            }
    }

    override fun getQuestions(quizId: String, result: (UiState<List<QuestionModel>>) -> Unit) {
        firestore.collection("quizzyapp_quiz").document(quizId).collection("questions")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val questions = arrayListOf<QuestionModel>()
                    for (document in it.result) {
                        val question = document.toObject(QuestionModel::class.java)
                        questions.add(question)
                    }
                    result.invoke(UiState.Success(questions))
                } else {
                    result.invoke(UiState.Failure(it.exception?.message.toString()))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.message.toString()))
            }
    }
}