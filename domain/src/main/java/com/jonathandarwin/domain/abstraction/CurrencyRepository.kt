package com.jonathandarwin.domain.abstraction

import com.jonathandarwin.domain.base.Response
import com.jonathandarwin.domain.dto.ConvertRequest
import com.jonathandarwin.domain.dto.ConvertResponse

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
interface CurrencyRepository {
    suspend fun convert(request: ConvertRequest): Response<ConvertResponse>
}