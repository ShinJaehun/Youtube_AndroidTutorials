package com.shinjaehun.onlineshopappdemo.model

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val cartItems: Map<String,Long> = emptyMap(),
    val address: String = ""

)
