package com.shinjaehun.quizonline

data class QuizModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String,
    val questionList: List<QuestionModel>
) {
    constructor() : this("", "", "", "", emptyList())
}