package com.jonathandarwin.currency.feature.history.model.action

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
sealed class HistoryUiAction {

    object DeleteAllHistory : HistoryUiAction()
}