package com.shinjaehun.quizzyapp.model

data class QuestionModel (
    val questionId: String="",
    val question: String="",
    val answer: String="",
    val option_a: String="",
    val option_b: String="",
    val option_c: String="",
    val timer: Int=0
)