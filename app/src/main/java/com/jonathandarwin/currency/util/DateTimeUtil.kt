package com.jonathandarwin.currency.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created By : Jonathan Darwin on March 27, 2021
 */
object DateTimeUtil {
    fun convertToDate(time: Long): Date {
        return Date(time)
    }

    fun Date.ddMMMyyyyHHmm(): String {
        val output = SimpleDateFormat("dd MMM yyyy HH:mm")
        return output.format(this)
    }
}