package com.jonathandarwin.domain.usecase

import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.convert.ConvertRequest
import com.jonathandarwin.domain.entity.CurrencyDTO
import com.jonathandarwin.domain.model.ConvertCurrency
import com.jonathandarwin.domain.model.Currency
import java.math.BigDecimal
import java.util.*
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

    override suspend fun convert(from: String, to: String, amount: String): Pair<Double, Double> {
        val response = currencyRepository.convert(ConvertRequest(from, to, amount))
        if(response is ApiResponse.Success) {
            return response.data.let {
                val result = when(to) {
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

                return Pair(result, it.result?.rate ?: 0.0)
            }
        }

        return Pair(0.0, 0.0)
    }

    override suspend fun saveConvertCurrency(convertCurrency: ConvertCurrency): Boolean {
        return convertCurrency.let {
            val currencyDTO = CurrencyDTO(UUID.randomUUID(), it.from, it.to, it.amount, it.result, it.rate, it.datetime)
            return currencyRepository.saveConvertCurrency(currencyDTO)
        }
    }

    override suspend fun getConvertCurrencyHistory(limit: Int): List<ConvertCurrency> {
        val response = currencyRepository.getConvertCurrencyHistory(limit)
        val result = arrayListOf<ConvertCurrency>()
        response.forEach {
            result.add(ConvertCurrency(it.from, it.to, it.amount, it.result, it.rate, it.datetime))
        }
        return result
    }

    override suspend fun deleteAllHistory(): Boolean {
        return currencyRepository.deleteAllHistory()
    }
}