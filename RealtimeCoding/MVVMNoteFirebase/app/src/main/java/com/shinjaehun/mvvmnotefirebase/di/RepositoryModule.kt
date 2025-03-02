package com.shinjaehun.mvvmnotefirebase.di

import com.google.firebase.firestore.FirebaseFirestore
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
}