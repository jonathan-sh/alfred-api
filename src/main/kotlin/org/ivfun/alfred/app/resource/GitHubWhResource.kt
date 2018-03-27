package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.document.GitHubWh
import org.ivfun.alfred.app.repository.GitHubWhRepository
import org.ivfun.alfred.app.service.git.GitHubWhService
import org.ivfun.mrt.resource.GenericResource
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by: jonathan
 * DateTime: 2018-03-21 23:48
 **/
@RestController
@RequestMapping(value = ["git-hub"])
class GitHubWhResource(val gitHubWhService: GitHubWhService,
                       val gitHubWhRepository: GitHubWhRepository,
                       val responseTreatment: ResponseTreatment<GitHubWh>)
    : GenericResource<GitHubWh>(gitHubWhRepository, responseTreatment)
{

    @PostMapping
    override
    fun create(@RequestBody gitHubWh: GitHubWh): ResponseEntity<Any> = gitHubWhService.tryDoBuild(gitHubWh)
}
