package com.jonathandarwin.currency.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jonathandarwin.currency.ui.history.HistoryViewModel
import com.jonathandarwin.currency.ui.history.HistoryViewModelState
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.ConvertCurrency
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*

/**
 * Created By : Jonathan Darwin on October 20, 2021
 */
class HistoryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var currencyUseCase: CurrencyUseCase

    private lateinit var viewModel: HistoryViewModel

    @MockK
    private lateinit var stateObserver: Observer<HistoryViewModelState>

    @MockK
    private lateinit var loadingObserver: Observer<Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HistoryViewModel(currencyUseCase)

        Dispatchers.setMain(TestCoroutineDispatcher())

        every { stateObserver.onChanged(any()) }.just(Runs)
        every { loadingObserver.onChanged(any()) }.just(Runs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get history, then it should update history latest data`() {
        val data = mutableListOf<ConvertCurrency>()
        data.add(ConvertCurrency("IDR", "USD", "10000", "1", "1", 123))
        data.add(ConvertCurrency("IDR", "USD", "10000", "1", "1", 123))

        coEvery { currencyUseCase.getConvertCurrencyHistory(any()) } returns data

        viewModel.loading.observeForever(loadingObserver)
        viewModel.state.observeForever(stateObserver)

        viewModel.getHistory()

        verifySequence {
            loadingObserver.onChanged(true)
            loadingObserver.onChanged(false)
            stateObserver.onChanged(HistoryViewModelState.GET_HISTORY)
        }

        assertThat(viewModel.historyList, `is`(data))
    }
}