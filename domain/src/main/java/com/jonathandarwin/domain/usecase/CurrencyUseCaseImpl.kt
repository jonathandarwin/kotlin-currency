package com.jonathandarwin.domain.usecase

import com.jonathandarwin.domain.abstraction.repository.CurrencyRepository
import com.jonathandarwin.domain.abstraction.usecase.CurrencyUseCase
import javax.inject.Inject

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
class CurrencyUseCaseImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository
): CurrencyUseCase {

    override fun convert(from: String, to: String) {

    }
}