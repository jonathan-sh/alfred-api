package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.repository.BuildRepository
import org.ivfun.alfred.app.service.build.BuildService
import org.ivfun.alfred.app.service.build.impl.FeedBack
import org.ivfun.mrt.resource.GenericResource
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 10:18
 **/

@RestController
@RequestMapping(value = ["/build"])
class BuildResource(val buildService: BuildService,
                    val buildRepository: BuildRepository,
                    val responseTreatment: ResponseTreatment<Build>)
: GenericResource<Build>(buildRepository, responseTreatment)
{
    @GetMapping(value = ["/log/{id}"])
    fun getLog(@PathVariable id: String): String = buildService.getLog(id)

    @PostMapping(value = ["/feed-back"])
    fun feedBack(@RequestBody feedBack: FeedBack) = buildService.saveFeedBack(feedBack)

}


