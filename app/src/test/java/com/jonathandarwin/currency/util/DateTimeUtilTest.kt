package com.jonathandarwin.currency.util

import com.jonathandarwin.currency.util.DateTimeUtil.ddMMMyyyyHHmm
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created By : Jonathan Darwin on April 03, 2021
 */
class DateTimeUtilTest {

    @Test
    fun `test_convert_date_ddMMyyyy`() {
        val time = 1617422567477
        assertEquals(DateTimeUtil.convertToDate(time).ddMMMyyyyHHmm(), "03 Apr 2021 11:02")
    }
}