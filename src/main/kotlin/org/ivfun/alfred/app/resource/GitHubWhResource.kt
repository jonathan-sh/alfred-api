package org.ivfun.alfred.app.resource


import org.ivfun.alfred.app.document.GitHubWh
import org.ivfun.alfred.app.service.git.GitHubWhService
import org.ivfun.alfred.app.usefull.AppConstant
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-21 23:48
 **/
@RestController
@RequestMapping(value = ["/wh-git-hub"])
class GitHubWhResource(val gitHubWhService: GitHubWhService)

{
    @PostMapping
    fun create(@RequestBody gitHubWh: GitHubWh,
               @RequestHeader(value = AppConstant.GIT_EVENT_HEADER) event : String): ResponseEntity<Any>
    {
        return gitHubWhService.tryDoBuild(gitHubWh,event)
    }

    @GetMapping
    fun get() = gitHubWhService.getList();

}
