package com.jonathandarwin.domain.dto.convert

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
data class ConvertResponse(
    val base: String?,
    val amount: Double?,
    val result: ConvertItemResponse?,
    val ms: Int?
)

data class ConvertItemResponse(
    val USD: Double?,
    val SGD: Double?,
    val JPY: Double?,
    val KRW: Double?,
    val MYR: Double?,
    val HKD: Double?,
    val CNY: Double?,
    val AUD: Double?,

    val rate: Double?
)