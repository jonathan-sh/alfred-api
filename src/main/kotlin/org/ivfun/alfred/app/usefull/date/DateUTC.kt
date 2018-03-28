package org.ivfun.alfred.app.usefull.date

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

/**
 * Created by: jonathan
 * DateTime: 2018-02-11 04:22
 **/
object DateUTC
{
    fun nowPlusLong(time: Long?): Date
    {
        val sec: Long = Timestamp.valueOf(LocalDateTime.now().plusSeconds(time!!)).time
        return Date(sec)
    }
}