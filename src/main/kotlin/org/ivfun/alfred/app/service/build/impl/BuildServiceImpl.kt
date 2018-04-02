package org.ivfun.alfred.app.service.build.impl

import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.repository.BuildRepository
import org.ivfun.alfred.app.service.build.BuildService
import org.ivfun.alfred.app.usefull.bash.Pipeline
import org.ivfun.alfred.app.usefull.enuns.BuildStatus
import org.ivfun.alfred.integration.slack.SlackMessage
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 22:42
 **/
@Service
class BuildServiceImpl(val buildRepository: BuildRepository,
                       val slackMessage: SlackMessage)
    : BuildService
{
    override
    fun processFeedBack(feedBack: FeedBack)
    {

        if (feedBack.build_id != null)
        {
           val optionalBuild: Optional<Build> = buildRepository.findById(feedBack.build_id)
           when(feedBack.status)
           {
               0 ->   { optionalBuild.ifPresent { build -> buildNotOK(build)} }
               1 ->   { optionalBuild.ifPresent { build -> buildOK(build, feedBack)} }
               2 ->   { optionalBuild.ifPresent { build -> restarting(build)} }
               else ->{ optionalBuild.ifPresent { build -> buildNotOK(build)} }
           }
        }
    }

    private fun buildOK(build: Build, feedBack: FeedBack)
    {
        checkToSend(build,feedBack)
        checkToDeploy(build)
        restarting(build)
    }

    private fun buildNotOK(build: Build)
    {
        build.setFail()
        saveAndSend(build)
    }

    private fun checkToSend(build: Build, feedBack: FeedBack)
    {
        build.setCompleted(feedBack)
        saveAndSend(build)
        build.setToSend()
        saveAndSend(build)
    }

    private fun restarting(build: Build)
    {
        build.setSuccessfully()
        saveAndSend(build)
    }

    private fun checkToDeploy(build: Build)
    {
        if (build.status == BuildStatus.SENDING_ARTIFACT)
        {
            if (Pipeline.send(build))
            {
                Pipeline.deploy(build)
                build.setDeploy()
            }
            else
            {
                build.setFail()
            }
            saveAndSend(build)
        }
    }

    private fun saveAndSend(build: Build)
    {
        buildRepository.save(build)
        slackMessage.send(build)
    }

    override
    fun getLog(id: String): String
    {
        val stringBuilder: StringBuilder = StringBuilder()
        return try
        {
            buildRepository.findById(id).ifPresent { build ->
                Files.lines(Paths.get(build.log_file)).forEach { l -> stringBuilder.append("<t style='font-family:\"Courier New\", Courier, monospace'>$l</t><br/>") }
            }
            stringBuilder.toString()
        }
        catch (e: Exception)
        {
            stringBuilder.append("<t style='font-family:\"Courier New\", Courier, monospace'>LOG NOT AVAILABLE :( </t><br/>").toString()
        }


    }

}