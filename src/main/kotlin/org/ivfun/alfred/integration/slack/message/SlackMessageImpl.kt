package org.ivfun.alfred.integration.slack.message

import com.google.gson.Gson
import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.usefull.AppConstant
import org.ivfun.alfred.integration.slack.SlackMessage
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 10:37
 **/
@Service
class SlackMessageImpl(val appConstant: AppConstant) : SlackMessage
{
    override
    fun send(build: Build): Boolean?
    {
        val restTemplate = RestTemplate()
        val slack_wh_msg_url: String = appConstant.SLACK_WH_MSG_URL
        val alfred_back_url: String = appConstant.ALFRED_BACK_URL
        try
        {
            val mapOf = mapOf("attachments" to SlackMessageUtil.messageFromBuild(build, alfred_back_url))
            val request = HttpEntity(Gson().toJson(mapOf))
            val response = restTemplate.exchange<String>(slack_wh_msg_url, HttpMethod.POST, request, String::class.java)
            if (response.statusCode == HttpStatus.OK)
            {
                return true
            }
        }
        catch (e: Exception)
        {
            println("fail")
        }
        return false
    }


}