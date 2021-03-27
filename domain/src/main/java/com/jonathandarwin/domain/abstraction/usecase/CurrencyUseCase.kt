package com.jonathandarwin.domain.abstraction.usecase

import com.jonathandarwin.domain.dto.currencies.CurrenciesResponse
import com.jonathandarwin.domain.model.Currency

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
interface CurrencyUseCase {
    suspend fun getCurrencies(): List<Currency>
    suspend fun convert(from: String, to: String): Double
}