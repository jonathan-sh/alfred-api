package org.ivfun.alfred.app.service.application.impl

import org.ivfun.alfred.app.document.Application
import org.ivfun.alfred.app.document.Slave
import org.ivfun.alfred.app.repository.ApplicationRepository
import org.ivfun.alfred.app.service.application.ApplicationService
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

/**
 * Created by: jonathan
 * DateTime: 2018-03-28 11:23
 **/
@Service
class ApplicationServiceImpl(private val applicationRepository: ApplicationRepository,
                             private val responseTreatment: ResponseTreatment<Application>)
:ApplicationService
{
    override
    fun create(@RequestBody application: Application): ResponseEntity<Any>
    {
        if (alreadyExistsToCreate(application))
        {
            return duplicateKey(application)
        }
        return responseTreatment.create(applicationRepository,application)
    }

    override
    fun update(@RequestBody application: Application): ResponseEntity<Any>
    {
        if (alreadyExistsToUpdade(application))
        {
            return duplicateKey(application)
        }
        return responseTreatment.update(applicationRepository,application)
    }


    private fun alreadyExistsToCreate(application: Application) :Boolean
    {
        return application.name != null && applicationRepository.findByName(application.name).isPresent
    }

    private fun alreadyExistsToUpdade(application: Application) :Boolean
    {
        var already :Boolean = false
        if(application.name != null && application.id != null)
        {
            applicationRepository.findByName(application.name)
                                 .ifPresent { found -> already = application.id != found.id }
        }
        return already
    }

    private fun duplicateKey(application: Application): ResponseEntity<Any>
    {
        val mapOf = mapOf("cause" to "duplicate key [name] < " + application.name + " >")
        return ResponseEntity<Any>(mapOf,HttpStatus.NOT_ACCEPTABLE)
    }
}