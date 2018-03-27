package org.ivfun.alfred.integration.slack.message

import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.usefull.BuildStatus

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 12:19
 **/
object SlackMessageUtil
{
    fun messageFromBuild(build: Build, alfred_back_url: String): List<Attachment>
    {
        val author_icon = build.gitHubWh?.sender?.avatar_url
        val author_name = build.gitHubWh?.sender?.login + " | " + build.gitHubWh?.head_commit?.message
        val author_link = build.gitHubWh?.head_commit?.url
        val title = build.status.toString().toLowerCase() + " | build : " + build.id_friendly + " | time : " + build.time() + "s"
        val title_link = "$alfred_back_url/build/log/" + build.id
        val fields: MutableList<Field> = mutableListOf()
        fields.add(Field("machine", build.slave?.name + " | " + build.slave?.ip))
        fields.add(Field("application", build.application?.name + " | " + build.branch))

        val color = when (build.status)
        {
            BuildStatus.DISCARDED -> "#000000"
            BuildStatus.WAITING -> "#ffcf00"
            BuildStatus.IN_PROGRESS -> "#125dee"
            BuildStatus.SENDED -> "#a9a9a9"
            BuildStatus.COMPLETED -> "#ff7845"
            BuildStatus.FAIL -> "#ff2930"
            BuildStatus.SUCCESSFULLY -> "#2ea664"
            else -> "#000000"
        }
        return listOf(Attachment(author_icon, author_link, author_name, color, title, title_link, fields))

    }
}






