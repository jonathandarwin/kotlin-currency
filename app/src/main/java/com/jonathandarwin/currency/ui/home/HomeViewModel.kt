package com.jonathandarwin.currency.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.currency.base.dialog.ListBottomSheet
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import com.jonathandarwin.domain.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
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

    var from = "IDR"
    var to = "USD"

    fun getCurrency() {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val result = currencyUseCase.getCurrencies()
                currencies.clear()
                result.forEach{
                    currencies.add(ListBottomSheet(it.name, it.code))
                }
                state.postValue(HomeViewModelState.SUCCES_GET_CURRENCY)
            }
            catch (e: Exception) {
                onError(e)
            }
        }
    }
}