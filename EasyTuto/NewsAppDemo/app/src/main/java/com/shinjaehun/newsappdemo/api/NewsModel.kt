package com.shinjaehun.newsappdemo.api

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)