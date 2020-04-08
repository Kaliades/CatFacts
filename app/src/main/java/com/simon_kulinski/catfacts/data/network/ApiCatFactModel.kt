package com.simon_kulinski.catfacts.data.network

import com.google.gson.annotations.SerializedName
import com.simon_kulinski.catfacts.domain.models.CatFact

data class ApiCatFactModel(
    @SerializedName("_id")
    val id: String,
    val text: String,
    @SerializedName("updatedAt")
    val update: String
) {

    fun toDomainModel(): CatFact {
        return CatFact(
            id, text, update
        )
    }


}