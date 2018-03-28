package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.service.email.Email
import org.ivfun.alfred.app.service.email.EmailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 18:15
 **/
@RestController
@RequestMapping(value = ["email"])
class EmailResource(val emailService: EmailService)
{
    @PostMapping
    fun post(@RequestBody email: Email ) = emailService.send(email)
}