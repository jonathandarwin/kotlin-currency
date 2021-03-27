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
    val IDR: String?,
    val USD: String?,
    val SGD: String?,
    val JPY: String?,
    val KRW: String?,
    val MYR: String?,
    val HKD: String?,
    val CNY: String?,
    val AUD: String?,

    val rate: String?
)