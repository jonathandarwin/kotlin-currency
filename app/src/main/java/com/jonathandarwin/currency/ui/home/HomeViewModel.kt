package com.jonathandarwin.currency.ui.home

import androidx.lifecycle.MutableLiveData
import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.currency.base.dialog.ListBottomSheet
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
class HomeViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : BaseViewModel() {

    val state = MutableLiveData<HomeViewModelState>()
    var currencies = arrayListOf<ListBottomSheet>()
    var history = arrayListOf<ConvertCurrency>()

    var from = "IDR"
    var to = "USD"

    var amount = ""
    var convertResult = ""
    var rate = ""

    fun getCurrency() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = currencyUseCase.getCurrencies()
                currencies.clear()
                result.forEach{
                    currencies.add(ListBottomSheet(it.name, it.code))
                }
                state.postValue(HomeViewModelState.SUCCESS_GET_CURRENCY)
            }
            catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun convert(amount: String) {
        this.amount = amount
        CoroutineScope(Dispatchers.IO).launch {
            try{
                loading.postValue(true)
                val pair = currencyUseCase.convert(from, to, amount)

                convertResult = pair.first.toString()
                rate = pair.second.toString()
                saveConvertCurrency(ConvertCurrency(from, to, amount, convertResult, rate, System.currentTimeMillis()))
                getPreviewHistory()

                loading.postValue(false)
                state.postValue(HomeViewModelState.SUCCESS_CONVERT)
            }
            catch (e: Exception) {
                onError(e)
            }
        }
    }

    suspend fun saveConvertCurrency(convertCurrency: ConvertCurrency) {
        currencyUseCase.saveConvertCurrency(convertCurrency)
    }

    fun getPreviewHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = currencyUseCase.getConvertCurrencyHistory(10)
            history.clear()
            history.addAll(result)
            state.postValue(HomeViewModelState.GET_HISTORY)
        }
    }
}