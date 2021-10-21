package com.jonathandarwin.currency.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jonathandarwin.currency.base.dialog.ListBottomSheet
import com.jonathandarwin.currency.ui.history.HistoryViewModel
import com.jonathandarwin.currency.ui.history.HistoryViewModelState
import com.jonathandarwin.currency.ui.home.HomeViewModel
import com.jonathandarwin.currency.ui.home.HomeViewModelState
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.Currency
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

/**
 * Created By : Jonathan Darwin on October 21, 2021
 */
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var currencyUseCase: CurrencyUseCase

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var stateObserver: Observer<HomeViewModelState>

    @MockK
    private lateinit var loadingObserver: Observer<Boolean>

    @MockK
    private lateinit var errorObserver: Observer<Throwable>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())

        viewModel = HomeViewModel(currencyUseCase)

        every { stateObserver.onChanged(any()) }.just(Runs)
        every { loadingObserver.onChanged(any()) }.just(Runs)
        every { errorObserver.onChanged(any()) }.just(Runs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get currency, if success, it should emit all the available currency`() {
        val data = mutableListOf<Currency>()
        data.add(Currency("Rupiah", "IDR"))
        data.add(Currency("Singapore Dollar", "SGD"))

        val mappedData = mutableListOf<ListBottomSheet>()
        data.forEach {
            mappedData.add(ListBottomSheet(it.name, it.code))
        }

        coEvery { currencyUseCase.getCurrencies() } returns data

        viewModel.loading.observeForever(loadingObserver)
        viewModel.state.observeForever(stateObserver)

        viewModel.getCurrency()

        verifySequence {
            stateObserver.onChanged(HomeViewModelState.SUCCESS_GET_CURRENCY)
        }

        MatcherAssert.assertThat(viewModel.currencies, `is`(mappedData))
    }

    @Test
    fun `when get currency, if error, it should emit error message`() {
        val error = Exception("Network Error")
        coEvery { currencyUseCase.getCurrencies() } throws error

        viewModel.loading.observeForever(loadingObserver)
        viewModel.error.observeForever(errorObserver)

        viewModel.getCurrency()

        verifySequence {
            loadingObserver.onChanged(false)
            errorObserver.onChanged(error)
        }
    }
}