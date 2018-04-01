package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.document.Application
import org.ivfun.alfred.app.repository.ApplicationRepository
import org.ivfun.alfred.app.service.application.ApplicationService
import org.ivfun.mrt.resource.GenericResource
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 10:18
 **/

@RestController
@RequestMapping(value = ["/application"])
class ApplicationResource(val applicationRepository: ApplicationRepository,
                          val applicationService: ApplicationService,
                          val responseTreatment: ResponseTreatment<Application>)
: GenericResource<Application>(applicationRepository, responseTreatment)
{
    @PostMapping
    override fun create(@RequestBody application: Application): ResponseEntity<Any> =  applicationService.create(application)

    @PutMapping
    override fun update(@RequestBody application: Application): ResponseEntity<Any> =  applicationService.update(application)
}

