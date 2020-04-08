package com.simon_kulinski.catfacts.data

import com.simon_kulinski.catfacts.data.network.CatFactsService
import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CatFactsRepositoryImpl(
    private val catFactsService: CatFactsService,
    private val dispatcher: CoroutineDispatcher
) :
    CatFactsRepository {

    override suspend fun getListOfCatFacts(): RequestResult<List<CatFact>> {
        return withContext(dispatcher) {
            try {
                val result = mapResultListToDomainList()
                return@withContext RequestResult.onSuccess(result)
            } catch (e: Exception) {
                return@withContext RequestResult.onFailure(e)
            }
        }
    }

    private suspend fun mapResultListToDomainList(): List<CatFact> {
        return catFactsService.getListOfCatFacts().map { it.toDomainModel() }
    }

    override suspend fun getCatFact(id: String): RequestResult<CatFact> {
        return withContext(dispatcher) {
            try {
                val result = catFactsService.getCatFactsById(id).toDomainModel()
                RequestResult.onSuccess(result)
            } catch (e: Exception) {
                RequestResult.onFailure(e)
            }
        }
    }
}