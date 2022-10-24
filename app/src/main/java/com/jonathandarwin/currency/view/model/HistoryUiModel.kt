package com.jonathandarwin.currency.view.model

import com.jonathandarwin.currency.base.model.NetworkResult
import com.jonathandarwin.domain.model.ConvertCurrency

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
data class HistoryUiModel(
    val data: NetworkResult<List<ConvertCurrency>>
) {
    companion object {
        val Empty: HistoryUiModel
            get() = HistoryUiModel(
                data = NetworkResult.Unknown
            )
    }
}