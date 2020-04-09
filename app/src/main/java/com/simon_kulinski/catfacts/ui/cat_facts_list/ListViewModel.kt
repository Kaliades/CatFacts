package com.simon_kulinski.catfacts.ui.cat_facts_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: CatFactsRepository
) : ViewModel() {

    private val _liveDataListOfCatFactsResult by lazy { MutableLiveData<RequestResult<List<CatFact>>>() }
    val liveDataListOfCatFactsResult: LiveData<RequestResult<List<CatFact>>> =
        _liveDataListOfCatFactsResult

    fun initData() {
        if (_liveDataListOfCatFactsResult.value == null)
            getDataFromRepository()
    }

    fun getNewData() {
        getDataFromRepository()
    }

    private fun getDataFromRepository() {
        viewModelScope.launch {
            _liveDataListOfCatFactsResult.value = repository.getListOfCatFacts()
        }
    }
}