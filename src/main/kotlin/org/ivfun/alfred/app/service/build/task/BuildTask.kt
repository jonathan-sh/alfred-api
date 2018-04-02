package org.ivfun.alfred.app.service.build.task

import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.repository.BuildRepository
import org.ivfun.alfred.app.usefull.bash.Pipeline
import org.ivfun.alfred.app.usefull.enuns.BuildStatus
import org.ivfun.alfred.integration.slack.SlackMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 21:47
 **/

@Component
class BuildTask(val buildRepository: BuildRepository,
                val slackMessage: SlackMessage)
{

    @Scheduled(fixedRateString = "5000")
    fun doBuilds()
    {
        val builds: List<Build> = buildRepository.findByStatus(BuildStatus.WAITING).sortedBy { s -> s.id_friendly }

        builds.forEach { build ->
            if (build.application?.name != null &&
                build.application.type != null &&
                build.branch != null &&
                build.id != null)
            {
                if (toDiscard(build))
                {
                    build.setToDiscard()
                    saveAndSend(build)
                }
                else
                {
                    build.setInProgress()
                    saveAndSend(build)
                    Pipeline.build(build)
                }
            }
        }
    }

    private fun saveAndSend(build: Build)
    {
        buildRepository.save(build)
        slackMessage.send(build)
    }

    private fun toDiscard(build: Build): Boolean
    {
        return buildRepository.findByStatus(BuildStatus.WAITING)
                              .filter { f -> equals(f, build) }
                              .count() > 0

    }

    private fun equals(a: Build, b: Build): Boolean
    {
        return try
        {
            a.application?.name.equals(b.application?.name) &&
            a.slave?.ip.equals(b.slave?.ip) &&
            a.branch.equals(b.branch) &&
            a.id_friendly!! > b.id_friendly!!
        }
        catch (e: Exception)
        {
            false
        }

    }

}