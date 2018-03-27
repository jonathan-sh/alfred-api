package org.ivfun.alfred.app.usefull

import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 14:44
 **/
@Configuration
class AppConstant(final val environment: Environment)
{
    val ALFRED_FRONT_URL: String = environment.getProperty("alfred.front-url")!!
    val ALFRED_BACK_URL: String = environment.getProperty("alfred.back-url")!!
    val SLACK_WH_MSG_URL: String = environment.getProperty("slack.wh-msg-url")!!
}

