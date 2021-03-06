package com.jonathandarwin.domain.dto.convert

import com.jonathandarwin.domain.base.BaseRequest

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */ 
data class ConvertRequest(
    val from: String,
    val to: String,
    val amount: String
) : BaseRequest()