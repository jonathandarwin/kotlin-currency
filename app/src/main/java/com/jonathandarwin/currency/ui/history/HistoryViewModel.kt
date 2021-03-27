package com.jonathandarwin.currency.ui.history

import androidx.lifecycle.MutableLiveData
import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.ConvertCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
        private val currencyUseCase: CurrencyUseCase
) : BaseViewModel() {

    val state = MutableLiveData<HistoryViewModelState>()

    val historyList = arrayListOf<ConvertCurrency>()


    fun getHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            val result = currencyUseCase.getConvertCurrencyHistory(50)
            historyList.clear()
            historyList.addAll(result)
            loading.postValue(false)
            state.postValue(HistoryViewModelState.GET_HISTORY)
        }
    }

    fun deleteAllHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            currencyUseCase.deleteAllHistory()
            loading.postValue(false)
            state.postValue(HistoryViewModelState.DELETE_ALL_HISTORY)
        }
    }
}