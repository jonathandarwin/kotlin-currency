package com.jonathandarwin.currency.util

import com.jonathandarwin.currency.util.DateTimeUtil.ddMMMyyyyHHmm
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created By : Jonathan Darwin on April 03, 2021
 */
class DateTimeUtilTest {

    @Test
    fun `test convert date dd MMM yyyy HHmm`() {
        val currentDate = Date()
        val output = SimpleDateFormat("dd MMM yyyy HH:mm")
        val result = output.format(currentDate)
        assertEquals(DateTimeUtil.convertToDate(currentDate.time).ddMMMyyyyHHmm(), result)
    }
}