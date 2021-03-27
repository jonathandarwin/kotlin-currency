package com.jonathandarwin.domain.usecase

import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.convert.ConvertRequest
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
        return if(response is ApiResponse.Success) {
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

    override suspend fun convert(from: String, to: String): Double {
        val response = currencyRepository.convert(ConvertRequest(from, to))
        if(response is ApiResponse.Success) {
            return response.data.let {
                return when(to) {
                    "IDR" -> it.result?.IDR ?: 0.0
                    "USD" -> it.result?.USD ?: 0.0
                    "SGD" -> it.result?.SGD ?: 0.0
                    "JPY" -> it.result?.JPY ?: 0.0
                    "KRW" -> it.result?.KRW ?: 0.0
                    "MYR" -> it.result?.MYR ?: 0.0
                    "HKD" -> it.result?.HKD ?: 0.0
                    "CNY" -> it.result?.CNY ?: 0.0
                    "AUD" -> it.result?.AUD ?: 0.0
                    else -> 0.0
                }
            }
        }

        return 0.0
    }
}