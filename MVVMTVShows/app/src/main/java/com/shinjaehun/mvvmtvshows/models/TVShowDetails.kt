package com.shinjaehun.mvvmtvshows.models

data class TVShowDetails (
    val url: String,
    val description: String,
    val runtime: String,
    val image_path: String,
    val rating: String,
    val genres: List<String>,
    val pictures: List<String>,
    val episodes: List<Episode>
)
