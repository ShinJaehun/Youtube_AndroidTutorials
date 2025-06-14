package com.shinjaehun.quizzyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shinjaehun.quizzyapp.repository.AuthRepository
import com.shinjaehun.quizzyapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository
): ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>> = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>> = _login

    fun signUp(email: String, password: String) {
        repository.signUp(email, password){
            _register.value = it
        }
    }

    fun signIn(email: String, password: String) {
        repository.signIn(email, password) {
            _login.value = it
        }
    }

    fun signOut(result: () -> Unit) {
        repository.signOut(result)
    }

    fun getSession(result: (FirebaseUser?) -> Unit){
        repository.getCurrentUser(result)
    }

}