package com.jonathandarwin.domain.dto.currencies

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
data class CurrenciesResponse(
    val currencies: ArrayList<CurrenciesItemResponse>?
)

data class CurrenciesItemResponse(
    val name: String?,
    val code: String?
)