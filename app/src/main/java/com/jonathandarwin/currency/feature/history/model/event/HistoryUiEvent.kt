package com.jonathandarwin.currency.feature.history.model.event

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
sealed class HistoryUiEvent {

    object HistoryDeleted : HistoryUiEvent()
}