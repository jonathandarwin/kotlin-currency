package com.jonathandarwin.currency.feature.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.currency.base.dialog.ListBottomSheet
import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.currency.feature.home.model.ConversionResultUiModel
import com.jonathandarwin.currency.feature.home.model.HomeUiState
import com.jonathandarwin.currency.feature.home.model.HomeViewModelState
import com.jonathandarwin.currency.feature.home.model.action.HomeUiAction
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.ConvertCurrency
import com.jonathandarwin.domain.util.launchWithCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : BaseViewModel() {

    val currencies: List<ListBottomSheet>
        get() = _currencies.value

    val from: String
        get() = _from.value

    val to: String
        get() = _to.value

    private val _from = MutableStateFlow("IDR")
    private val _to = MutableStateFlow("USD")
    private val _currencies = MutableStateFlow<List<ListBottomSheet>>(emptyList())
    private val _histories = MutableStateFlow<List<ConvertCurrency>>(emptyList())
    private val _conversionResult = MutableStateFlow(ConversionResultUiModel.Empty)

    val state: Flow<HomeUiState> = combine(
        _from,
        _to,
        _currencies,
        _histories,
        _conversionResult
    ) { from, to, currencies, histories, conversionResult ->
        HomeUiState(
            from = from,
            to = to,
            currencies = currencies,
            histories = histories,
            conversionResult = conversionResult
        )
    }

    init {
        getCurrency()
        getPreviewHistory()
    }

    fun submitAction(action: HomeUiAction) {
        when(action) {
            is HomeUiAction.SetFrom -> handleSetFrom(action.from)
            is HomeUiAction.SetTo -> handleSetTo(action.to)
            is HomeUiAction.Convert -> handleConvert(action.amount)
            is HomeUiAction.LoadHistory -> handleLoadHistory()
        }
    }

    private fun handleSetFrom(from: String) {
        _from.value = from
    }

    private fun handleSetTo(to: String) {
        _to.value = to
    }

    private fun handleConvert(amount: String) {
        viewModelScope.launchWithCatch(block = {
            if(_conversionResult.value.state == NetworkResult.Loading) return@launchWithCatch

            _conversionResult.updateData(NetworkResult.Loading)
            val result = currencyUseCase.convert(from, to, amount)
            val conversionResult = ConversionResultUiModel.Result(
                amount = amount,
                convertResult = result.first.toString(),
                rate = result.second.toString()
            )

            saveConvertCurrency(conversionResult)
            getPreviewHistory()

            _conversionResult.updateData(NetworkResult.Success(conversionResult))
        }) {
            _conversionResult.updateData(NetworkResult.Error(it))
        }
    }

    private fun handleLoadHistory() {
        getPreviewHistory()
    }

    private fun getCurrency() {
        viewModelScope.launchWithCatch(block = {
            val result = currencyUseCase.getCurrencies().map {
                ListBottomSheet(it.name, it.code)
            }
            _currencies.value = result
        }) { }
    }

    private suspend fun saveConvertCurrency(conversionResult: ConversionResultUiModel.Result) {
        currencyUseCase.saveConvertCurrency(
            ConvertCurrency(
                from = from,
                to = to,
                amount = conversionResult.amount,
                result = conversionResult.convertResult,
                rate = conversionResult.rate,
                datetime = System.currentTimeMillis()
            )
        )
    }

    private fun getPreviewHistory() {
        viewModelScope.launchWithCatch(block = {
            val result = currencyUseCase.getConvertCurrencyHistory(10)
            _histories.value = result
        }) { }
    }

    private fun MutableStateFlow<ConversionResultUiModel>.updateData(state: NetworkResult<ConversionResultUiModel.Result>) {
        value = _conversionResult.value.copy(
            state = state
        )
    }
}