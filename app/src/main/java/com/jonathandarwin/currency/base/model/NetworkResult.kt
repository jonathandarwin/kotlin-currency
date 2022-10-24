package com.jonathandarwin.currency.base.model


/**
 * Created By : Jonathan Darwin on October 24, 2022
 */
sealed class NetworkResult<out T> {

    object Unknown : NetworkResult<Nothing>()

    object Loading : NetworkResult<Nothing>()

    data class Success<T>(val data: T) : NetworkResult<T>()

    data class Error(val throwable: Throwable) : NetworkResult<Nothing>()
}