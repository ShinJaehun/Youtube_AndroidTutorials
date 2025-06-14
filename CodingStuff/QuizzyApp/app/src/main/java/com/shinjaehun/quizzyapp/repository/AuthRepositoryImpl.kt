package com.shinjaehun.quizzyapp.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shinjaehun.quizzyapp.util.UiState

class AuthRepositoryImpl(
    val auth: FirebaseAuth
): AuthRepository {

    val firebaseUserLiveData = MutableLiveData<FirebaseUser>()

    override fun getCurrentUser(result: (FirebaseUser?) -> Unit) {
        result.invoke(auth.currentUser)
    }

    override fun signUp(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUserLiveData.postValue(auth.currentUser)
                    result.invoke(
                        UiState.Success("SignUp successfully")
                    )
                } else {
                    result.invoke(
                        UiState.Failure("SignUp failed : " + it.exception?.message)
                    )
                }
            }
    }

    override fun signIn(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUserLiveData.postValue(auth.currentUser)
                    result.invoke(
                        UiState.Success("SignIn successfully")
                    )
                } else {
                    result.invoke(
                        UiState.Failure("SignIn failed: " + it.exception?.message)
                    )
                }
            }
    }

    override fun signOut(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }

}