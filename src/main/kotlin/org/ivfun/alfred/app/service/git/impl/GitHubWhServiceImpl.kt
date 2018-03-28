package org.ivfun.alfred.app.service.git.impl

import org.ivfun.alfred.app.document.Application
import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.document.GitHubWh
import org.ivfun.alfred.app.repository.ApplicationRepository
import org.ivfun.alfred.app.repository.BuildRepository
import org.ivfun.alfred.app.repository.GitHubWhRepository
import org.ivfun.alfred.app.repository.SlaveRepository
import org.ivfun.alfred.app.service.git.GitHubWhService
import org.ivfun.alfred.app.usefull.date.LdtUtc
import org.ivfun.alfred.app.usefull.enuns.BuildStatus
import org.ivfun.alfred.app.usefull.enuns.GitHubWhStatus
import org.ivfun.alfred.integration.slack.SlackMessage
import org.ivfun.mrt.sequence.impl.SequenceService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:04
 **/
@Service
class GitHubWhServiceImpl(val applicationRepository: ApplicationRepository,
                          val buildRepository: BuildRepository,
                          val slaveRepository: SlaveRepository,
                          val gitHubWhRepository: GitHubWhRepository,
                          val sequenceService: SequenceService,
                          val slackMessage: SlackMessage)
    : GitHubWhService
{
    override
    fun tryDoBuild(gitHubWh: GitHubWh): ResponseEntity<Any>
    {
        val branch: String? = gitHubWh.ref?.replace("refs/heads/", "")
        val appName: String? = gitHubWh.repository?.name
        val builds: ArrayList<Build> = ArrayList<Build>()

        slaveRepository.findAll().forEach { slave ->
            if (branch != null && appName != null)
            {
                val application: Application = applicationRepository.findByName(appName)
                if (application.enable!! &&
                        slave.branches!!.contains(branch) &&
                        slave.applications!!.contains(application.id))
                {
                    val next: Long = sequenceService.next("build", 1)
                    builds.add(Build(null, next, application, branch, gitHubWh, slave, BuildStatus.WAITING, LdtUtc().nowArray()))
                }

            }
        }

        builds.forEach { build ->
            buildRepository.save(build)
            slackMessage.send(build)
        }

        gitHubWh.status =
                if (builds.size > 0)
                {
                    GitHubWhStatus.ACCEPTED
                }
                else
                {
                    GitHubWhStatus.NOT_ACCEPTED
                }
        gitHubWhRepository.save(gitHubWh)

        return ResponseEntity.ok(gitHubWh.status!!)
    }

}