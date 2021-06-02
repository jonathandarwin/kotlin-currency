package com.jonathandarwin.data.repository

import android.app.Service
import com.jonathandarwin.data.database.AppDatabase
import com.jonathandarwin.data.database.dao.CurrencyDAO
import com.jonathandarwin.data.network.RemoteService
import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.convert.ConvertItemResponse
import com.jonathandarwin.domain.dto.convert.ConvertRequest
import com.jonathandarwin.domain.dto.convert.ConvertResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


/**
 * Created By : Jonathan Darwin on April 03, 2021
 */
class CurrencyRepositoryImplTest{

    lateinit var currencyRepository: CurrencyRepositoryImpl

    @MockK
    lateinit var service: RemoteService

    @MockK
    lateinit var currencyDao: CurrencyDAO

    lateinit var request: ConvertRequest
    lateinit var response: Response<ConvertResponse>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        currencyRepository = CurrencyRepositoryImpl(service, currencyDao, Dispatchers.IO)
        `prepare req res`()
    }

    fun `prepare req res`() {
        request = ConvertRequest("IDR", "USD", "15000")
        request.apiKey = "demo"

        response = Response.success(ConvertResponse("IDR", 15000.0, ConvertItemResponse(
            null,
            1.00,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            1.00), 7))
    }

    @Test
    fun `test convert`() {
        runBlocking {
            every {
                runBlocking { service.convert(request.from, request.to, request.amount, request.apiKey) }
            } returns response

            val result = currencyRepository.convert(request)
            assertEquals(result, ApiResponse.Success(response.body()!!))
        }
    }
}