package com.jonathandarwin.domain.abstraction.usecase

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
interface CurrencyUseCase {
    fun convert(from: String, to: String)
}