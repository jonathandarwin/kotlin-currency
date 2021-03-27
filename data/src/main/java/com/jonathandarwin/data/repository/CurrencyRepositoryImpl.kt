package com.jonathandarwin.data.repository

import com.jonathandarwin.data.database.AppDatabase
import com.jonathandarwin.data.network.RemoteService
import com.jonathandarwin.data.network.RetrofitBuilder
import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.convert.ConvertRequest
import com.jonathandarwin.domain.dto.convert.ConvertResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesItemResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesResponse
import com.jonathandarwin.domain.entity.CurrencyDTO
import java.lang.Exception
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class CurrencyRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService,
    private val database: AppDatabase
) : CurrencyRepository {

    override suspend fun convert(request: ConvertRequest): ApiResponse<ConvertResponse> {
        val response = remoteService.convert(request.from, request.to, request.amount, RetrofitBuilder.apiKey)
        if(response.isSuccessful){
            return ApiResponse.Success(response.body()!!)
        }
        else {
            throw Exception("Please try again.")
        }
    }

    override suspend fun getCurrencies(): ApiResponse<CurrenciesResponse> {
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
        return ApiResponse.Success<CurrenciesResponse>(response)
    }

    override suspend fun saveConvertCurrency(currenyDTO: CurrencyDTO): Boolean {
        return database.currencyDAO.insert(currenyDTO) > 0
    }

    override suspend fun getConvertCurrencyHistory(limit: Int): List<CurrencyDTO> {
        return database.currencyDAO.get()
    }

    override suspend fun deleteAllHistory(): Boolean {
        return database.currencyDAO.deleteAll() > 0
    }
}