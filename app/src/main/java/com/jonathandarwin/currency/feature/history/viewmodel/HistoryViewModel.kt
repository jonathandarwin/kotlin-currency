package com.jonathandarwin.currency.feature.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.currency.feature.history.model.HistoryUiModel
import com.jonathandarwin.currency.feature.history.model.HistoryUiEvent
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.ConvertCurrency
import com.jonathandarwin.domain.util.launchWithCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : BaseViewModel() {

    private val _histories = MutableStateFlow(HistoryUiModel.Empty)
    val histories: Flow<HistoryUiModel>
        get() = _histories

    private val _event = MutableSharedFlow<HistoryUiEvent>()
    val event: Flow<HistoryUiEvent>
        get() = _event

    init {
        getHistory()
    }

    private fun getHistory() {
        viewModelScope.launchWithCatch(block = {
            _histories.updateData(data = NetworkResult.Loading)
            val result = currencyUseCase.getConvertCurrencyHistory(HISTORY_LIMIT)
            _histories.updateData(data = NetworkResult.Success(result))
        }) {
            _histories.updateData(data = NetworkResult.Error(it))
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launchWithCatch(block = {
            _histories.updateData(data = NetworkResult.Loading)
            currencyUseCase.deleteAllHistory()
            _event.emit(HistoryUiEvent.HistoryDeleted)
        }) {
            _histories.updateData(data = NetworkResult.Error(it))
        }
    }

    private fun MutableStateFlow<HistoryUiModel>.updateData(data: NetworkResult<List<ConvertCurrency>>) {
        value = _histories.value.copy(
            data = data
        )
//        update {
//            it.copy(
//                data = data
//            )
//        }
    }

    companion object {
        private const val HISTORY_LIMIT = 50
    }
}