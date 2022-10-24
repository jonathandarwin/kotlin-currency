package com.jonathandarwin.currency.feature.home.model

import com.jonathandarwin.currency.base.dialog.ListBottomSheet
import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.domain.model.ConvertCurrency

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
data class HomeUiState(
    val from: String,
    val to: String,
    val currencies: List<ListBottomSheet>,
    val histories: List<ConvertCurrency>,
    val conversionResult: ConversionResultUiModel
)

data class ConversionResultUiModel(
    val state: NetworkResult<Result>
) {
    data class Result(
        val amount: String,
        val convertResult: String,
        val rate: String
    )

    companion object {
        val Empty: ConversionResultUiModel
            get() = ConversionResultUiModel(
                state = NetworkResult.Unknown
            )
    }
}