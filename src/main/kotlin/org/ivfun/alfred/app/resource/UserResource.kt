package org.ivfun.alfred.app.resource


import org.ivfun.alfred.app.document.User
import org.ivfun.alfred.app.service.security.AuthService
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 18:57
 **/
@RestController
@RequestMapping(value = ["/user"])
class UserResource(val authService: AuthService)
{
    @PostMapping()
    fun create(@RequestBody user: User)=authService.create(user)

    @PutMapping()
    fun update(@RequestBody user: User)=authService.update(user)
}