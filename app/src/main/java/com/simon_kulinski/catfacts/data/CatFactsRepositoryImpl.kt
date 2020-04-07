package com.simon_kulinski.catfacts.data

import com.simon_kulinski.catfacts.domain.repositories.CatFatsRepository
import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class CatFactsRepositoryImpl(private val catFactService: CatFactService) :
    CatFatsRepository {

    override suspend fun getListOfCatFacts(): RequestResult<List<CatFact>> {
        return withContext(IO) {
            try {
                val result = parseResultToDomainList()
                return@withContext RequestResult.onSuccess(result)
            } catch (e: Exception) {
                return@withContext RequestResult.onFailure(e)
            }
        }
    }

    private suspend fun parseResultToDomainList(): List<CatFact> {
        return catFactService.getListOfCatFacts().map { it.toDomainModel() }
    }

    override suspend fun getCatFacts(id: String): RequestResult<CatFact> {
        return withContext(IO) {
            try {
                val result = catFactService.getCatFactsById(id).toDomainModel()
                RequestResult.onSuccess(result)
            } catch (e: Exception) {
                RequestResult.onFailure(e)
            }
        }
    }
}