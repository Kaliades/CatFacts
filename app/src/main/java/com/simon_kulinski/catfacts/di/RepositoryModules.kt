package com.simon_kulinski.catfacts.di

import com.simon_kulinski.catfacts.data.CatFactsRepositoryImpl
import com.simon_kulinski.catfacts.data.NetworkManagerImpl
import com.simon_kulinski.catfacts.data.network.CatFactsService
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import com.simon_kulinski.catfacts.domain.repositories.NetworkManager
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryModule = module {
    single { CatFactsService.createCatFactService() }
    single<CatFactsRepository> {
        CatFactsRepositoryImpl(
            get(), Dispatchers.IO
        )
    }
    single<NetworkManager> { NetworkManagerImpl(androidContext()) }
}


