package com.jonathandarwin.domain.abstraction.repository

import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.convert.ConvertRequest
import com.jonathandarwin.domain.dto.convert.ConvertResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesResponse

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
interface CurrencyRepository {
    suspend fun getCurrencies(): ApiResponse<CurrenciesResponse>
    suspend fun convert(request: ConvertRequest): ApiResponse<ConvertResponse>
}