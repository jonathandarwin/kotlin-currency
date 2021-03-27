package com.jonathandarwin.data.repository

import com.jonathandarwin.data.network.RemoteService
import com.jonathandarwin.domain.abstraction.CurrencyRepository
import com.jonathandarwin.domain.base.Response
import com.jonathandarwin.domain.dto.ConvertRequest
import com.jonathandarwin.domain.dto.ConvertResponse
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class CurrencyRepositoryImpl @Inject constructor(
    private val remoteService: RemoteService
) : CurrencyRepository {

    override suspend fun convert(request: ConvertRequest): Response<ConvertResponse> {
        TODO("Not yet implemented")
    }
}