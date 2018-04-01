package org.ivfun.alfred.app.service.application

import org.ivfun.alfred.app.document.Application
import org.springframework.http.ResponseEntity

/**
 * Created by: jonathan
 * DateTime: 2018-03-28 15:31
 **/
interface ApplicationService
{
    fun create(application: Application): ResponseEntity<Any>
    fun update(application: Application): ResponseEntity<Any>
}