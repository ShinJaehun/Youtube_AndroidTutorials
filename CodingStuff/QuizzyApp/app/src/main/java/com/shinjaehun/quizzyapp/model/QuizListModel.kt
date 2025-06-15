package com.shinjaehun.quizzyapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizListModel(
    var quizId: String="",
    val title: String="",
    val image: String="",
    val questions: String="",
    val difficulty: String="",
): Parcelable
