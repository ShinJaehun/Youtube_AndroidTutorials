package com.shinjaehun.imagefilterapp.dependencyinjection

import com.shinjaehun.imagefilterapp.repositories.EditImageRepository
import com.shinjaehun.imagefilterapp.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
}