package org.ivfun.alfred.app.service.git.impl

import org.ivfun.alfred.app.document.Application
import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.document.GitHubWh
import org.ivfun.alfred.app.document.Slave
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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

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
    fun tryDoBuild(gitHubWh: GitHubWh, event: String): ResponseEntity<Any>
    {
        val branch: String? = gitHubWh.ref?.replace("refs/heads/", "")
        gitHubWh.ref = branch
        gitHubWh.id_friendly = sequenceService.next("git-hub-wh", 1)
        gitHubWh.event = event
        val appName: String? = gitHubWh.repository?.name
        val builds: ArrayList<Build> = ArrayList<Build>()

        if (branch != null && appName != null)
        {
            slaveRepository.findAll()
            .forEach { slave ->

                val found: Optional<Application> = applicationRepository.findByName(appName)

                if (found.isPresent)
                {
                    val present: Application = found.get()
                    if (present.name != null)
                    {
                        if (validBuild(present, slave, branch))
                        {
                            val next: Long = sequenceService.next("build", 1)
                            builds.add(Build(null, next, present, branch, gitHubWh, slave, BuildStatus.WAITING, LdtUtc().nowArray()))
                        }
                    }
                    else
                    {
                        gitHubWh.errors.add("Not found application for build")
                    }
                }

            }
        }

        if (builds.size == 0)
        {
            gitHubWh.errors.add("Not found machine for build")
            gitHubWh.status = GitHubWhStatus.NOT_ACCEPTED
        }
        else
        {
            builds.forEach { build ->
                buildRepository.save(build)
                slackMessage.send(build)
            }

            gitHubWh.status = GitHubWhStatus.ACCEPTED
        }

        gitHubWhRepository.save(gitHubWh)

        return ResponseEntity<Any>(gitHubWh,HttpStatus.OK)
    }

    private fun validBuild(application: Application, slave: Slave, branch: String): Boolean
    {
        return try
        {
            application.enable!! &&
            slave.branches!!.contains(branch) &&
            slave.applications!!.contains(application.name)
        }
        catch (e: Exception)
        {
            false
        }
    }

    override fun getList(): ResponseEntity<Any>
    {
        val mapOf = mapOf("githubwhs" to gitHubWhRepository.findAll())
        return  ResponseEntity<Any>(mapOf, HttpStatus.OK)
    }
}