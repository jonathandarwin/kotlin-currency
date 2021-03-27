package com.jonathandarwin.domain.base

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
sealed class Response<out R> {
    data class Success<out T>(val data: T): Response<T>()
    data class Error(val data: Throwable): Response<Nothing>()
}