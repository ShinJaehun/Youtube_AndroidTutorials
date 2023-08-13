package com.shinjaehun.imagefilterapp.utilities

import android.app.Application
import com.shinjaehun.imagefilterapp.dependencyinjection.repositoryModule
import com.shinjaehun.imagefilterapp.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}