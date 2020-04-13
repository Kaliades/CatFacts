package com.simon_kulinski.catfacts

import android.app.Application
import com.simon_kulinski.catfacts.di.repositoryModule
import com.simon_kulinski.catfacts.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatFactsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatFactsApp)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}