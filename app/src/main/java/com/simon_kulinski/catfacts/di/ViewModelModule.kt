package com.simon_kulinski.catfacts.di

import com.simon_kulinski.catfacts.ui.cat_fact_details.DetailsViewModel
import com.simon_kulinski.catfacts.ui.cat_facts_list.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ListViewModel(get(), get())
    }
    viewModel { (id: String) ->
        DetailsViewModel(id, get())
    }
}