package com.jonathandarwin.currency.builder

import com.jonathandarwin.domain.model.ConvertCurrency
import com.jonathandarwin.domain.model.Currency

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
class CurrencyModelBuilder {

    fun buildConvertCurrency(
        size: Int = 2
    ) = List(size) {
        ConvertCurrency("IDR", "USD", "10000", "1", "1", 123)
    }

    fun buildCurrencies(
        size: Int = 2
    ) = List(size) {
        Currency("Rupiah $it", "IDR")
    }
}