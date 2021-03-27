package com.jonathandarwin.domain.abstraction.usecase

import com.jonathandarwin.domain.model.ConvertCurrency
import com.jonathandarwin.domain.model.Currency

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
interface CurrencyUseCase {
    suspend fun getCurrencies(): List<Currency>
    suspend fun convert(from: String, to: String, amount: String): Pair<Double, Double>
    suspend fun saveConvertCurrency(convertCurrency: ConvertCurrency): Boolean
    suspend fun getConvertCurrencyHistory(limit: Int): List<ConvertCurrency>
    suspend fun deleteAllHistory(): Boolean
}