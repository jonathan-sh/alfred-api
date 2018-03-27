package org.ivfun.alfred.integration.slack

import org.ivfun.alfred.app.document.Build

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 17:46
 **/
interface SlackMessage
{
    fun send(build: Build): Boolean?
}