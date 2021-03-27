package com.jonathandarwin.domain.usecase

import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.base.Response
import com.jonathandarwin.domain.dto.currencies.CurrenciesResponse
import com.jonathandarwin.domain.model.Currency
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class CurrencyUseCaseImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository
): CurrencyUseCase {

    override suspend fun getCurrencies(): List<Currency> {
        val response = currencyRepository.getCurrencies()
        return if(response is Response.Success) {
            val data = response.data
            val result = arrayListOf<Currency>()
            data.currencies?.forEach {
                result.add(Currency(it.name, it.code))
            }
            result
        }
        else{
            arrayListOf()
        }
    }

    override suspend fun convert(from: String, to: String) {
        TODO("Not yet implemented")
    }
}