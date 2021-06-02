package com.jonathandarwin.data.repository

import com.jonathandarwin.data.database.AppDatabase
import com.jonathandarwin.data.database.dao.CurrencyDAO
import com.jonathandarwin.data.network.RemoteService
import com.jonathandarwin.data.network.RetrofitBuilder
import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.convert.ConvertRequest
import com.jonathandarwin.domain.dto.convert.ConvertResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesItemResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesResponse
import com.jonathandarwin.domain.entity.CurrencyDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class CurrencyRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService,
    private val currencyDAO: CurrencyDAO,
    private val dispatcher: CoroutineDispatcher
) : CurrencyRepository {

    override suspend fun convert(request: ConvertRequest): ApiResponse<ConvertResponse> {
        return withContext(dispatcher) {
            val response = remoteService.convert(request.from, request.to, request.amount, RetrofitBuilder.apiKey)
            if(response.isSuccessful){
                ApiResponse.Success(response.body()!!)
            }
            else {
                throw Exception("Please try again.")
            }
        }
    }

    override suspend fun getCurrencies(): ApiResponse<CurrenciesResponse> {
        return withContext(dispatcher) {
            val currencies = arrayListOf<CurrenciesItemResponse>()
            currencies.add(CurrenciesItemResponse("Indonesian Rupiah", "IDR"))
            currencies.add(CurrenciesItemResponse("United States Dollar", "USD"))
            currencies.add(CurrenciesItemResponse("Japanese Yen", "JPY"))
            currencies.add(CurrenciesItemResponse("South Korean Won", "KRW"))
            currencies.add(CurrenciesItemResponse("Malaysian Ringgit", "MYR"))
            currencies.add(CurrenciesItemResponse("Hong Kong Dollar", "HKD"))
            currencies.add(CurrenciesItemResponse("Chinese Yuan", "HKD"))
            currencies.add(CurrenciesItemResponse("Australian Dollar", "AUD"))
            val response = CurrenciesResponse(currencies)
            ApiResponse.Success<CurrenciesResponse>(response)
        }
    }

    override suspend fun saveConvertCurrency(currenyDTO: CurrencyDTO): Boolean {
        return withContext(dispatcher) {
            currencyDAO.insert(currenyDTO) > 0
        }
    }

    override suspend fun getConvertCurrencyHistory(limit: Int): List<CurrencyDTO> {
        return withContext(dispatcher) {
            currencyDAO.get()
        }
    }

    override suspend fun deleteAllHistory(): Boolean {
        return withContext(dispatcher) {
            currencyDAO.deleteAll() > 0
        }
    }
}