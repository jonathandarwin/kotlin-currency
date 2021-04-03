package com.jonathandarwin.domain.usecase

import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.base.ApiResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesItemResponse
import com.jonathandarwin.domain.dto.currencies.CurrenciesResponse
import com.jonathandarwin.domain.model.Currency
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Created By : Jonathan Darwin on April 03, 2021
 */
class CurrencyUseCaseImplTest{

    private lateinit var currencyUseCase: CurrencyUseCase

    @MockK
    lateinit var currencyRepository: CurrencyRepository

    val currenciesResponse = arrayListOf<CurrenciesItemResponse>()

    val currencyList = arrayListOf<Currency>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        currencyUseCase = CurrencyUseCaseImpl(currencyRepository)

        `prepare currencies response`()
        `prepare currencies result`()
    }

    private fun `prepare currencies response`() {
        currenciesResponse.add(CurrenciesItemResponse("Indonesian Rupiah", "IDR"))
        currenciesResponse.add(CurrenciesItemResponse("United States Dollar", "USD"))
        currenciesResponse.add(CurrenciesItemResponse("Japanese Yen", "JPY"))
        currenciesResponse.add(CurrenciesItemResponse("South Korean Won", "KRW"))
        currenciesResponse.add(CurrenciesItemResponse("Malaysian Ringgit", "MYR"))
        currenciesResponse.add(CurrenciesItemResponse("Hong Kong Dollar", "HKD"))
        currenciesResponse.add(CurrenciesItemResponse("Chinese Yuan", "HKD"))
        currenciesResponse.add(CurrenciesItemResponse("Australian Dollar", "AUD"))
    }

    private fun `prepare currencies result`() {
        currencyList.add(Currency("Indonesian Rupiah", "IDR"))
        currencyList.add(Currency("United States Dollar", "USD"))
        currencyList.add(Currency("Japanese Yen", "JPY"))
        currencyList.add(Currency("South Korean Won", "KRW"))
        currencyList.add(Currency("Malaysian Ringgit", "MYR"))
        currencyList.add(Currency("Hong Kong Dollar", "HKD"))
        currencyList.add(Currency("Chinese Yuan", "HKD"))
        currencyList.add(Currency("Australian Dollar", "AUD"))
    }

    @Test
    fun `test get currencies`() {
        runBlocking {
            val apiResponse = ApiResponse.Success(CurrenciesResponse(currenciesResponse))
            every { runBlocking { currencyRepository.getCurrencies() } } returns apiResponse

            val result = currencyUseCase.getCurrencies()
            assertEquals(result, currencyList)
        }
    }
}