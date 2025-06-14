package com.shinjaehun.quizzyapp.repository

import com.google.firebase.auth.FirebaseUser
import com.shinjaehun.quizzyapp.util.UiState

interface AuthRepository {
    fun signUp(email: String, password: String, result: (UiState<String>) -> Unit)
    fun signIn(email: String, password: String, result: (UiState<String>) -> Unit)
    fun signOut(result: () -> Unit)
    fun getCurrentUser(result: (FirebaseUser?) -> Unit)
}