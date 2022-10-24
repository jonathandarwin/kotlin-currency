package com.jonathandarwin.currency.builder

/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
class CommonModelBuilder {

    fun buildException(
        message: String = "Something went wrong"
    ) = Exception(message)
}