package com.jonathandarwin.domain.base

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val data: Throwable): ApiResponse<Nothing>()
}