package org.ivfun.alfred.app.usefull

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * Created by: jonathan
 * DateTime: 2018-02-11 04:22
 **/
open class LdtUtc
{
    private val zoneUTC: ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC)

    fun now(): LocalDateTime = zoneUTC.toLocalDateTime()

    fun nowArray(): Array<Int>
    {
        val ldt: LocalDateTime = zoneUTC.toLocalDateTime()
        return arrayOf(ldt.year, ldt.monthValue, ldt.dayOfMonth, ldt.hour, ldt.minute, ldt.second)
    }

    fun fromArray(array: Array<Int>?): LocalDateTime
    {
        if (array != null && array.size > 4)
        {
            return LocalDateTime.of(array[0], array[1], array[2], array[3], array[4], array[5])
        }
        return now()
    }


}