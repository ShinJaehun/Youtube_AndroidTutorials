package com.shinjaehun.mvvmnotefirebase.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import com.shinjaehun.mvvmnotefirebase.data.model.Note
import com.shinjaehun.mvvmnotefirebase.data.model.User
import com.shinjaehun.mvvmnotefirebase.util.FirestoreCollection
import com.shinjaehun.mvvmnotefirebase.util.FirestoreDocumentField
import com.shinjaehun.mvvmnotefirebase.util.SharedPrefConstants
import com.shinjaehun.mvvmnotefirebase.util.UiState

private const val TAG = "AuthRepositoryImpl"

class AuthRepositoryImpl(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
    val appPreferences: SharedPreferences,
    val gson: Gson
): AuthRepository {
    override fun registerUser(
        email: String,
        password: String,
        user: User,
        result: (UiState<String>) -> Unit
    ) {

        Log.i(TAG, "user before update: $user")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.id = it.result.user?.uid ?: "" // 여기서 user.id를 넣어줘야 합니다!
                    updateUserInfo(user) { state ->
                        when(state) {
                            is UiState.Success -> {
                                storeSession(
                                    id = it.result.user?.uid ?: ""
                                ) {
                                    if(it == null) {
                                        result.invoke(UiState.Failure("User register successfully buf session failed to store"))
                                    } else {
                                        result.invoke(
                                            UiState.Success("User register successfully")
                                        )
                                    }
                                }
                            }
                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(state.error))
                            }
                            else -> Unit
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, email already registered"))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message.toString()))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun updateUserInfo(user: User, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirestoreCollection.USER).document(user.id) // 이젠 auth의 uid를 사용하고 있음!
//        user.id = document.id // current user의 id를 적용해야 할꺼 아냐?
        document
            .set(user)
            .addOnSuccessListener {
                result.invoke(UiState.Success("User has been updated successfully"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }

    override fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    storeSession(id = task.result.user?.uid ?: "") {
                        if (it == null) {
                            result.invoke(UiState.Failure("Failed to store session"))
                        } else {
                            result.invoke(UiState.Success("Login successfully!"))
                        }
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, Check email and password"))
            }
    }

    override fun forgotPassword(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if(task.isSuccessful) {
                        result.invoke(UiState.Success("Email has been sent"))
                    } else {
                        result.invoke(UiState.Failure(task.exception?.message.toString()))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, Check email"))
            }
    }

    override fun logout(result: () -> Unit) {
        auth.signOut()
        appPreferences.edit().putString(SharedPrefConstants.USER_SESSION, null).apply()
        result.invoke()
    }

    override fun storeSession(id: String, result: (User?) -> Unit) {
        database.collection(FirestoreCollection.USER).document(id)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    val user = it.result.toObject(User::class.java)
                    appPreferences
                        .edit()
                        .putString(SharedPrefConstants.USER_SESSION, gson.toJson(user))
                        .apply()
                    result.invoke(user)
                } else {
                    result.invoke(null)
                }
            }
            .addOnFailureListener {
                result.invoke(null)
            }
    }

    override fun getSession(result: (User?) -> Unit) {
        // auth.currentUser()로 간단히 처리할 수 있는데... 이런 원리라는 걸 이해하란 말이지.
        val user_str = appPreferences.getString(SharedPrefConstants.USER_SESSION, null)
        if (user_str == null) {
            result.invoke(null)
        } else {
            val user = gson.fromJson(user_str, User::class.java)
            result.invoke(user)
        }
    }
}