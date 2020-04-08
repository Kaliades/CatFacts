package com.simon_kulinski.catfacts.domain.repositories

import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact

interface CatFactsRepository {

    suspend fun getListOfCatFacts(): RequestResult<List<CatFact>>

    suspend fun getCatFact(id: String): RequestResult<CatFact>


}