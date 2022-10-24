package com.jonathandarwin.currency.viewmodel

import com.jonathandarwin.currency.base.model.NetworkResult
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

/**
 * Created By : Jonathan Darwin on October 20, 2021
 */
class HistoryViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var currencyUseCase: CurrencyUseCase

    private lateinit var viewModel: HistoryViewModel

    private val mockConvertCurrencyResponse = listOf(
        ConvertCurrency("IDR", "USD", "10000", "1", "1", 123),
        ConvertCurrency("IDR", "USD", "10000", "1", "1", 123)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        coEvery { currencyUseCase.getConvertCurrencyHistory(any()) } returns mockConvertCurrencyResponse

        viewModel = HistoryViewModel(currencyUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when get history, then it should update history latest data`() {

        val states = recordStates { }

        when(val data = states[0].data) {
            is NetworkResult.Success -> {
                assertEquals(data.data, mockConvertCurrencyResponse)
            }
            else -> fail("Wrong state")
        }
    }

    @Test
    fun `when delete history, then it should update the state`() {
        coEvery { currencyUseCase.deleteAllHistory() } returns true

        val events = recordEvents {
            viewModel.submitAction(HistoryUiAction.DeleteAllHistory)
        }

        assertEquals(events[0], HistoryUiEvent.HistoryDeleted)
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