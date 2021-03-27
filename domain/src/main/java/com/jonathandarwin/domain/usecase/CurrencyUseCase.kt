package com.jonathandarwin.domain.usecase

import com.jonathandarwin.domain.abstraction.CurrencyRepository
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class CurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
){
    fun convert(from: String, to: String) {

    }
}