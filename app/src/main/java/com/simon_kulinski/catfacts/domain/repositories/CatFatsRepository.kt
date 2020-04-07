package com.simon_kulinski.catfacts.domain.repositories

import com.simon_kulinski.catfacts.domain.RequestResult
import com.simon_kulinski.catfacts.domain.models.CatFact

interface CatFatsRepository {

    suspend fun getListOfCatFacts(): RequestResult<List<CatFact>>

    suspend fun getCatFacts(id: String): RequestResult<CatFact>


}