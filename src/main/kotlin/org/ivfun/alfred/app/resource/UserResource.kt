package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.document.User
import org.ivfun.alfred.app.service.security.AuthService
import org.ivfun.alfred.app.service.security.ChangePasswordService
import org.ivfun.alfred.app.service.security.dto.ChangePassword
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 18:57
 **/
@RestController
@RequestMapping(value = ["/user"])
class UserResource(val authService: AuthService,
                   val changePasswordService: ChangePasswordService)
{
    @GetMapping()
    fun findAll():ResponseEntity<Any> = authService.findAll()

    @PostMapping()
    fun create(@RequestBody user: User)=authService.create(user)

    @PostMapping(value = ["/change-password"])
    fun changePassword(@RequestBody changePassword: ChangePassword) = changePasswordService.change(changePassword)

    @PutMapping()
    fun update(@RequestBody user: User)=authService.update(user)

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: String) = authService.delete(id)

}