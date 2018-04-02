package org.ivfun.alfred.app.service.build

import org.ivfun.alfred.app.service.build.impl.FeedBack

/**
 * Created by: jonathan
 * DateTime: 2018-03-23 09:02
 **/
interface BuildService
{
    fun processFeedBack(feedBack: FeedBack)

    fun getLog(id: String): String
}