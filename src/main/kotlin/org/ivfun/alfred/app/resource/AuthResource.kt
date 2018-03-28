package org.ivfun.som.security.resource

import org.ivfun.som.security.service.TokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 18:57
 **/
@RestController
@RequestMapping(value = ["/auth"])
class AuthResource(val service: TokenService)
{
    @GetMapping
    fun get() = service.generateToken()

    @GetMapping(value = ["valid"])
    fun getValid(@RequestHeader("x") token: String) = service.check(token)
}