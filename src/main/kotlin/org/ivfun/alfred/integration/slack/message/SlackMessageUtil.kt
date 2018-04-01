package org.ivfun.alfred.integration.slack.message

import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.usefull.enuns.BuildStatus

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
        val title = build.status.toString().toLowerCase() + " | build : " + build.id_friendly + " | total-time : " + build.time() + "s"
        val title_link = "$alfred_back_url/build/log/" + build.id
        val fields: MutableList<Field> = mutableListOf()
        fields.add(Field("machine", build.slave?.name + " | " + build.slave?.ip))
        fields.add(Field("application", build.application?.name + " | " + build.branch))

        val color:String = when (build.status)
        {
            BuildStatus.DISCARDED -> "#ff00aa"
            BuildStatus.WAITING -> "#ffcf00"
            BuildStatus.BUILDING_ARTIFACT -> "#125dee"
            BuildStatus.BUILD_COMPLETED -> "#7d52cc"
            BuildStatus.SENDING_ARTIFACT -> "#707070"
            BuildStatus.FAIL -> "#ff2930"
            BuildStatus.RESTARTING_SERVICE -> "#2ea664"
            BuildStatus.IN_DEPLOY -> "#00b2ff"
            else -> "#000000"
        }
        return listOf(Attachment(author_icon, author_link, author_name, color, title, title_link, fields))

    }
}






