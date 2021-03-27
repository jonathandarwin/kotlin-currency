package com.jonathandarwin.domain.model

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
data class ConvertCurrency(
    val from: String,
    val to: String,
    val amount: String,
    val result: String,
    val rate: String,
    val datetime: Long
)