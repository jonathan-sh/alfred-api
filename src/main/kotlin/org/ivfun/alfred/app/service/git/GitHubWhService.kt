package org.ivfun.alfred.app.service.git

import org.ivfun.alfred.app.document.GitHubWh
import org.springframework.http.ResponseEntity

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:12
 **/
interface GitHubWhService
{
    fun tryDoBuild(gitHubWh: GitHubWh): ResponseEntity<Any>
}