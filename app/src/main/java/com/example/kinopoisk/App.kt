package com.example.kinopoisk

import android.app.Application
import com.example.data.di.dataModule
import com.example.domain.di.interactorModule
import com.example.kinopoisk.di.appModule
import com.example.kinopoisk.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            loadKoinModules(modules)
        }
    }

    private val modules = viewModelModule + appModule + dataModule + interactorModule

}