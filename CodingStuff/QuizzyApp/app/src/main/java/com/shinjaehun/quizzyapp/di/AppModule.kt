package com.shinjaehun.quizzyapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shinjaehun.quizzyapp.repository.AuthRepository
import com.shinjaehun.quizzyapp.repository.AuthRepositoryImpl
import com.shinjaehun.quizzyapp.repository.QuizListRepository
import com.shinjaehun.quizzyapp.repository.QuizListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideQuizListRepository(
        firestore: FirebaseFirestore
    ): QuizListRepository {
        return QuizListRepositoryImpl(firestore)
    }
}