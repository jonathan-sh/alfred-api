package org.ivfun.alfred.app.service.security.impl

import org.ivfun.alfred.app.document.User
import org.ivfun.alfred.app.repository.UserRepository
import org.ivfun.alfred.app.service.security.AuthService
import org.ivfun.alfred.app.service.security.TokenService
import org.ivfun.alfred.app.service.security.dto.Profile
import org.ivfun.alfred.app.usefull.enuns.UserLevel
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


/**
 * Created by: jonathan
 * DateTime: 2018-03-27 21:22
 **/
@Service
class AuthServiceImpl(private val userRepository: UserRepository,
                      private val tokenService: TokenService,
                      private val responseTreatmentImpl: ResponseTreatment<User>)
:AuthService
{
    private val bCrypt: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override
    fun doLogin(user: User) : ResponseEntity<Any>
    {
        var isValidUser:Boolean = false
        var foundUser:User = User()

       if (user.email!=null && user.password!=null)
        {
            userRepository.findByEmail(user.email).ifPresent { found ->
                val passIsValid :Boolean = bCrypt.matches(user.password, found.password)
                foundUser = found
                isValidUser = passIsValid && found.enable!!
            }
        }

        return if (isValidUser)
        {
            val token:String = tokenService.generateToken(foundUser)
            val profile:Profile = Profile(token, foundUser)
            ResponseEntity<Any>(profile,HttpStatus.OK)
        }
        else
        {
            val mapOf = mapOf("cause" to "email/password invalid or user disable")
            ResponseEntity<Any>(mapOf,HttpStatus.UNAUTHORIZED)
        }

    }

    override
    fun createFirstAdminUser(): ResponseEntity<Any>
    {
        val pass :String = "admin"
        val email :String = "admin@mail.com"
        val mapOf = mapOf("cause" to "already exists at least one admin user")
        var response : ResponseEntity<Any> = ResponseEntity<Any>(mapOf, HttpStatus.NOT_ACCEPTABLE)
        val notContainsAdmUsers:Boolean = userRepository.findByLevel(UserLevel.ADMIN).count() == 0

        if (notContainsAdmUsers)
        {
            val encodePass :String = BCryptPasswordEncoder().encode(pass)
            val user = User(null,"admin",email,encodePass,UserLevel.ADMIN,true)
            userRepository.save(user)
            userRepository.findByEmail(email).ifPresent { found ->
                found.password = pass
                response = ResponseEntity<Any>(found, HttpStatus.OK)
            }
        }

        return response
    }

    override
    fun create(user: User): ResponseEntity<Any>
    {
       if (user.password!=null)
       {
           user.password = bCrypt.encode(user.password)
       }
       return responseTreatmentImpl.create(userRepository,user)
    }

    override
    fun update(user: User): ResponseEntity<Any>
    {
        if (user.password!=null && user.email != null)
        {
            val found:Optional<User> = userRepository.findByEmail(user.email)

            if (found.isPresent)
            {
                val isValidPass:Boolean = bCrypt.matches(user.password, found.get().password)
                if (isValidPass)
                {
                    user.password = bCrypt.encode(user.password)
                }
                else
                {
                    val mapOf = mapOf("cause" to "old password invalid")
                    return ResponseEntity<Any>(mapOf,HttpStatus.UNAUTHORIZED)
                }
            }
            else
            {
                val mapOf = mapOf("cause" to "user <" + user.email + "> not found")
                return ResponseEntity<Any>(mapOf,HttpStatus.UNAUTHORIZED)
            }

        }
        return responseTreatmentImpl.update(userRepository,user)
    }

    override
    fun findAll(): ResponseEntity<Any>
    {
        val findAll = userRepository.findAll()
        findAll.forEach { item -> item.removePass() }
        val mapOf = mapOf("users" to findAll)
        return ResponseEntity<Any>(mapOf,HttpStatus.OK)
    }

    override
    fun delete(id:String): ResponseEntity<Any>
    {
        var deleted: Boolean = false
        val found = userRepository.findById(id)
        found.ifPresent { user->
            userRepository.delete(user)
            deleted = true
        }
        return ResponseEntity<Any>(mapOf("deleted" to deleted),HttpStatus.OK)
    }
}