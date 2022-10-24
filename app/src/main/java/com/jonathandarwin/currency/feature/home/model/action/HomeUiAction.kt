package com.jonathandarwin.currency.feature.home.model.action

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
sealed class HomeUiAction {

    data class SetFrom(val from: String) : HomeUiAction()

    data class SetTo(val to: String) : HomeUiAction()

    data class Convert(val amount: String): HomeUiAction()
}