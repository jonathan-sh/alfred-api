package org.ivfun.alfred.app.resource


import org.ivfun.alfred.app.document.User
import org.ivfun.alfred.app.service.security.AuthService
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 18:57
 **/
@RestController
@RequestMapping(value = ["/auth"])
class AuthResource(val authService: AuthService)
{
    @GetMapping(value = ["/create-first-admin"])
    fun createFirstAdminUser() =  authService.createFirstAdminUser()

    @PostMapping()
    fun login(@RequestBody user: User) = authService.doLogin(user)

}