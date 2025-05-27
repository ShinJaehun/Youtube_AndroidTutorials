package com.shinjaehun.firebaseauthenticationdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class AuthViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

//    private val _authState = MutableLiveData<AuthState>()
//    val authState: LiveData<AuthState> = _authState

    private val _authState = MutableStateFlow<AuthState>(AuthState.UnAuthenticated)
//    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    val authState = _authState
        .onStart {
            if (auth.currentUser == null) {
                _authState.value = AuthState.UnAuthenticated
            } else {
                _authState.value = AuthState.Authenticated
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _authState.value
        )

    //.stateIn()이 없으면 UI에서 사용할 때 No value passed for parameter 'initialValue'.
    // val authState by authViewModel.authState.collectAsStateWithLifecycle()

//    init {
//        checkAuthStatus()
//    }
//
//    fun checkAuthStatus(){
//        if(auth.currentUser == null) {
//            _authState.value = AuthState.UnAuthenticated
//        } else {
//            _authState.value = AuthState.Authenticated
//        }
//    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
    }
}

sealed class AuthState {
    object Authenticated: AuthState()
    object UnAuthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
}