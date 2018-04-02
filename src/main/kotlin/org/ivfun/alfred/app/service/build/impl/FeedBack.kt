package org.ivfun.alfred.app.service.build.impl

/**
 * Created by: jonathan
 * DateTime: 2018-03-23 08:13
 **/
data class FeedBack
(
        val build_id: String? = null,
        val status: Int? = 0,
        val artifact: String? = null,
        val log: String? = null
)