package com.simon_kulinski.catfacts.ui.cat_fact_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simon_kulinski.catfacts.common.isValueEmpty
import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val id: String,
    private val repository: CatFactsRepository
) : ViewModel() {

    private val _liveDataDetailsCatFact: MutableLiveData<RequestResult<CatFact>> by lazy { MutableLiveData<RequestResult<CatFact>>() }
    val liveDataDetailsCatFact: LiveData<RequestResult<CatFact>> = _liveDataDetailsCatFact

    fun initData() {
        viewModelScope.launch {
            if (_liveDataDetailsCatFact.isValueEmpty())
                _liveDataDetailsCatFact.value = repository.getCatFact(id)
        }
    }


}