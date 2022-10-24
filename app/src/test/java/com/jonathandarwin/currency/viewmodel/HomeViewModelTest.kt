package com.jonathandarwin.currency.viewmodel

import com.jonathandarwin.currency.builder.CommonModelBuilder
import com.jonathandarwin.currency.builder.CurrencyModelBuilder
import com.jonathandarwin.currency.feature.home.model.HomeUiState
import com.jonathandarwin.currency.feature.home.viewmodel.HomeViewModel
import com.jonathandarwin.currency.feature.home.model.action.HomeUiAction
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created By : Jonathan Darwin on October 21, 2021
 */
class HomeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var currencyUseCase: CurrencyUseCase

    private lateinit var viewModel: HomeViewModel

    /** Builder */
    private val currencyBuilder = CurrencyModelBuilder()
    private val commonBuilder = CommonModelBuilder()

    /** Mock Response */
    private val mockCurrencyResponse = currencyBuilder.buildCurrencies()
    private val mockConvertCurrencyResponse = currencyBuilder.buildConvertCurrency()
    private val mockException = commonBuilder.buildException()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `home_getCurrency_success`() {
        coEvery { currencyUseCase.getCurrencies() } returns mockCurrencyResponse

        viewModel = HomeViewModel(currencyUseCase)

        val states = recordStates { }

        states[0].currencies.forEachIndexed { idx, e ->
            assertEquals(e.label, mockCurrencyResponse[idx].name)
            assertEquals(e.value, mockCurrencyResponse[idx].code)
        }
    }

    @Test
    fun `home_getCurrency_error`() {
        coEvery { currencyUseCase.getCurrencies() } throws mockException

        viewModel = HomeViewModel(currencyUseCase)

        val states = recordStates { }

        assertEquals(states[0].currencies.size, 0)
    }

    @Test
    fun `home_getPreviewHistory_success`() {
        coEvery { currencyUseCase.getConvertCurrencyHistory(any()) } returns mockConvertCurrencyResponse

        viewModel = HomeViewModel(currencyUseCase)

        val states = recordStates { }

        assertEquals(states.last().histories, mockConvertCurrencyResponse)
    }

    @Test
    fun `home_getPreviewHistory_error`() {
        coEvery { currencyUseCase.getConvertCurrencyHistory(any()) } throws mockException

        viewModel = HomeViewModel(currencyUseCase)

        val states = recordStates { }

        assertEquals(states.last().histories.size, 0)
    }

    @Test
    fun `home_setFrom`() {
        val from = "USD"

        viewModel = HomeViewModel(currencyUseCase)

        val states = recordStates {
            viewModel.submitAction(HomeUiAction.SetFrom(from))
        }

        assertEquals(states.last().from, from)
    }

    @Test
    fun `home_setTo`() {
        val from = "USD"

        viewModel = HomeViewModel(currencyUseCase)

        val states = recordStates {
            viewModel.submitAction(HomeUiAction.SetTo(from))
        }

        assertEquals(states.last().to, from)
    }

    /** TODO: add more unittest, e.g. convert case, save currency case */

    private fun recordStates(block: () -> Unit): List<HomeUiState> {
        val data = mutableListOf<HomeUiState>()
        val scope = CoroutineScope(testDispatcher)
        scope.launch {
            viewModel.state.collect {
                data.add(it)
            }
        }

        testDispatcher.runBlockingTest { block() }
        testDispatcher.advanceUntilIdle()
        scope.cancel()
        return data
    }
}