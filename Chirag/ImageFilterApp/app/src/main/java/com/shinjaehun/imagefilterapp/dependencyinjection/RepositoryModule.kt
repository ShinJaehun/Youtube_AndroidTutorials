package com.shinjaehun.imagefilterapp.dependencyinjection

import com.shinjaehun.imagefilterapp.repositories.EditImageRepository
import com.shinjaehun.imagefilterapp.repositories.EditImageRepositoryImpl
import com.shinjaehun.imagefilterapp.repositories.SavedImagesRepository
import com.shinjaehun.imagefilterapp.repositories.SavedImagesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImagesRepository> { SavedImagesRepositoryImpl(androidContext()) }
}