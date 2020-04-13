package com.simon_kulinski.catfacts.ui.cat_facts_list

import androidx.lifecycle.*
import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import com.simon_kulinski.catfacts.domain.repositories.NetworkManager
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: CatFactsRepository,
    networkManager: NetworkManager
) : ViewModel() {

    private val _liveDataListOfCatFactsResult by lazy { MutableLiveData<RequestResult<List<CatFact>>>() }
    val liveDataListOfCatFactsResult: LiveData<RequestResult<List<CatFact>>> =
        _liveDataListOfCatFactsResult
    private val _progressBarrLiveData by lazy { MutableLiveData(false) }
    val progressBarLiveData: LiveData<Boolean> = _progressBarrLiveData
    private var isConnected: Boolean = false

    val isNetworkConnection: LiveData<Boolean> = Transformations.switchMap(
        networkManager.isNetworkAvailable()
    ) { isAvailable ->
        isConnected = isAvailable
        getDataFromRepository()
        MutableLiveData(isAvailable)
    }


    fun getNewData() {
        getDataFromRepository()
    }

    private fun getDataFromRepository() {
        if (!isConnected)
            return
        _progressBarrLiveData.value = true
        viewModelScope.launch {
            _liveDataListOfCatFactsResult.value = repository.getListOfCatFacts()
            _progressBarrLiveData.value = false
        }
    }

}