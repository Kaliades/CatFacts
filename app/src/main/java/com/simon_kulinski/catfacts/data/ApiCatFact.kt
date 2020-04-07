package com.simon_kulinski.catfacts.data

import com.google.gson.annotations.SerializedName
import com.simon_kulinski.catfacts.domain.models.CatFact

data class ApiCatFact(
    @SerializedName("_id")
    val id: String,
    val text: String,
    @SerializedName("updatedAt")
    val update: Long
) {

    fun toDomainModel(): CatFact {
        return CatFact(
            id, text, update
        )
    }


}