package com.shinjaehun.mvvmtvshows.responses

import com.shinjaehun.mvvmtvshows.models.TVShowDetails

data class TVShowDetailsResponse (
    val tvShow: TVShowDetails
    // 이게 맞다... api가 tvShow라는 이름으로 reponse하기 때문...
    // tvShow 대신 다른 이름 사용하면 null만 받아 오게 됨
)