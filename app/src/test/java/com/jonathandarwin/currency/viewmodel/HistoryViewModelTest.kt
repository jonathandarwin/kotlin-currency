package com.jonathandarwin.currency.viewmodel

import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.currency.builder.CommonModelBuilder
import com.jonathandarwin.currency.builder.CurrencyModelBuilder
import com.jonathandarwin.currency.feature.history.model.event.HistoryUiEvent
import com.jonathandarwin.currency.feature.history.model.HistoryUiModel
import com.jonathandarwin.currency.feature.history.model.action.HistoryUiAction
import com.jonathandarwin.currency.feature.history.viewmodel.HistoryViewModel
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.ConvertCurrency
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import java.lang.Exception

/**
 * Created By : Jonathan Darwin on October 20, 2021
 */
class HistoryViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var currencyUseCase: CurrencyUseCase

    private lateinit var viewModel: HistoryViewModel

    /** Builder */
    private val currencyBuilder = CurrencyModelBuilder()
    private val commonBuilder = CommonModelBuilder()

    /** Mock Response */
    private val mockConvertCurrencyResponse = currencyBuilder.buildConvertCurrency()
    private val mockException = commonBuilder.buildException()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `history_getHistory_success`() {

        coEvery { currencyUseCase.getConvertCurrencyHistory(any()) } returns mockConvertCurrencyResponse

        viewModel = HistoryViewModel(currencyUseCase)

        val states = recordStates { }

        when(val data = states[0].data) {
            is NetworkResult.Success -> {
                assertEquals(data.data, mockConvertCurrencyResponse)
            }
            else -> fail("Wrong state")
        }
    }

    @Test
    fun `history_getHistory_error`() {

        coEvery { currencyUseCase.getConvertCurrencyHistory(any()) } throws mockException

        viewModel = HistoryViewModel(currencyUseCase)

        val states = recordStates { }

        when(val data = states[0].data) {
            is NetworkResult.Error -> {
                assertEquals(data.throwable, mockException)
            }
            else -> fail("Wrong state")
        }
    }


    @Test
    fun `history_deleteAll_success`() {
        coEvery { currencyUseCase.deleteAllHistory() } returns true

        viewModel = HistoryViewModel(currencyUseCase)

        val events = recordEvents {
            viewModel.submitAction(HistoryUiAction.DeleteAllHistory)
        }

        assertEquals(events[0], HistoryUiEvent.HistoryDeleted)
    }

    @Test
    fun `history_deleteAll_error`() {
        coEvery { currencyUseCase.deleteAllHistory() } throws mockException

        viewModel = HistoryViewModel(currencyUseCase)

        val events = recordEvents {
            viewModel.submitAction(HistoryUiAction.DeleteAllHistory)
        }

        assertEquals(events.size, 0)
    }

    private fun recordStates(block: () -> Unit): List<HistoryUiModel> {
        val data = mutableListOf<HistoryUiModel>()
        val scope = CoroutineScope(testDispatcher)
        scope.launch {
            viewModel.histories.collect {
                data.add(it)
            }
        }

        testDispatcher.runBlockingTest { block() }
        testDispatcher.advanceUntilIdle()
        scope.cancel()
        return data
    }

    private fun recordEvents(block: () -> Unit): List<HistoryUiEvent> {
        val data = mutableListOf<HistoryUiEvent>()
        val scope = CoroutineScope(testDispatcher)
        scope.launch {
            viewModel.event.collect {
                data.add(it)
            }
        }

        testDispatcher.runBlockingTest { block() }
        testDispatcher.advanceUntilIdle()
        scope.cancel()
        return data
    }
}