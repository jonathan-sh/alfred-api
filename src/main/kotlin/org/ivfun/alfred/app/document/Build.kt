package org.ivfun.alfred.app.document

import org.ivfun.alfred.app.service.build.impl.FeedBack
import org.ivfun.alfred.app.usefull.date.LdtUtc
import org.ivfun.alfred.app.usefull.enuns.BuildStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Duration

/**
 * Created by: jonathan
 * DateTime: 2018-03-21 23:00
 **/
@Document(collection = "build")
data class Build
(
    @Id
    val id: String? = null,
    val id_friendly: Long? = null,
    val application: Application? = null,
    val branch: String? = null,
    val gitHubWh: GitHubWh? = null,
    val slave: Slave? = null,
    var status: BuildStatus? = null,
    val start: List<Int>? = null,
    var finish: List<Int>? = null,
    var artifact_file: String? = null,
    var log_file: String? = null
)
{
    fun time(): Long = Duration.between(LdtUtc().fromArray(start), LdtUtc().fromArray(finish)).seconds

    fun setInProgress()
    {
        status = BuildStatus.IN_PROGRESS
        finish = LdtUtc().nowArray()
    }

    fun setToSend()
    {
        status = BuildStatus.SENDED
        finish = LdtUtc().nowArray()
    }

    fun setToDiscard()
    {
        status = BuildStatus.DISCARDED
        finish = LdtUtc().nowArray()
    }


    fun setCompleted(feedBack: FeedBack)
    {
        status = BuildStatus.COMPLETED
        finish = LdtUtc().nowArray()
        log_file = feedBack.log
        artifact_file = feedBack.artifact
    }


    fun setSuccessfully()
    {
        status = BuildStatus.SUCCESSFULLY
        finish = LdtUtc().nowArray()
    }

    fun setDeploy()
    {
        status = BuildStatus.IN_DEPLOY
        finish = LdtUtc().nowArray()
    }

    fun setFail()
    {
        status = BuildStatus.FAIL
        finish = LdtUtc().nowArray()
    }


    fun setFail(feedBack: FeedBack)
    {
        status = BuildStatus.FAIL
        finish = LdtUtc().nowArray()
        log_file = feedBack.log
        artifact_file = feedBack.artifact
    }
}