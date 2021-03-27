package com.jonathandarwin.currency.ui.home

import com.jonathandarwin.currency.base.BaseViewModel
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : BaseViewModel() {

}