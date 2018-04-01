package org.ivfun.alfred.app.service.security

import org.ivfun.alfred.app.document.User
import org.springframework.http.ResponseEntity

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 22:50
 **/
interface AuthService
{
    fun doLogin(user: User): ResponseEntity<Any>
    fun createFirstAdminUser(): ResponseEntity<Any>
    fun create(user: User): ResponseEntity<Any>
    fun update(user: User): ResponseEntity<Any>
    fun findAll(): ResponseEntity<Any>
    fun delete(id: String): ResponseEntity<Any>
}
