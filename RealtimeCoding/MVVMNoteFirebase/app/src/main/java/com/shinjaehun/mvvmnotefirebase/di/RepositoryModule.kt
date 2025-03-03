package com.shinjaehun.mvvmnotefirebase.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.shinjaehun.mvvmnotefirebase.data.repository.AuthRepository
import com.shinjaehun.mvvmnotefirebase.data.repository.AuthRepositoryImpl
import com.shinjaehun.mvvmnotefirebase.data.repository.NoteRepository
import com.shinjaehun.mvvmnotefirebase.data.repository.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore
    ): NoteRepository {
        return NoteRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        database: FirebaseFirestore,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImpl(auth, database, appPreferences, gson)
    }
}