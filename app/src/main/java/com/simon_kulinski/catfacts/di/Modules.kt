package com.simon_kulinski.catfacts.di

import com.simon_kulinski.catfacts.data.CatFactsRepositoryImpl
import com.simon_kulinski.catfacts.data.network.CatFactsService
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import com.simon_kulinski.catfacts.ui.cat_facts_list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules {
    fun get() = listOf(repositoryModule, viewModelModule)

    private val repositoryModule = module {
        single { CatFactsService.createCatFactService() }
        single<CatFactsRepository> {
            CatFactsRepositoryImpl(
                get(), Dispatchers.IO
            )
        }
    }

    private val viewModelModule = module {
        viewModel {
            ListViewModel(get())
        }
    }
}