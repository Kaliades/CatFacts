package com.simon_kulinski.catfacts

import android.app.Application
import com.simon_kulinski.catfacts.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CatFactsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CatFactsApp)
            modules(Modules.get())
        }
    }
}