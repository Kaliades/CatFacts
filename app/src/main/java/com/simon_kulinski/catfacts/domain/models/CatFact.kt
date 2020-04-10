package com.simon_kulinski.catfacts.domain.models

import java.sql.Timestamp
import java.text.SimpleDateFormat

data class CatFact(
    val id: String,
    val text: String,
    val updateTime: Timestamp
) {
    fun getUpdateTimeAsFormattedString(simpleDateFormat: SimpleDateFormat): String {
        return simpleDateFormat.format(updateTime)
    }
}