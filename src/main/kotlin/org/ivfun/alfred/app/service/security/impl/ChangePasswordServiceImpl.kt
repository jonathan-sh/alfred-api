package org.ivfun.alfred.app.service.security.impl

import org.ivfun.alfred.app.repository.UserRepository
import org.ivfun.alfred.app.service.security.ChangePasswordService
import org.ivfun.alfred.app.service.security.dto.ChangePassword
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 * Created by: jonathan
 * DateTime: 2018-04-01 00:37
 **/
@Service
class ChangePasswordServiceImpl(val userRepository: UserRepository)
: ChangePasswordService
{
    val bcry : BCryptPasswordEncoder = BCryptPasswordEncoder()

    override
    fun change(changePassword: ChangePassword): MutableMap<String, Any>
    {
        val mapOf:MutableMap<String,Any> = mutableMapOf<String,Any>()
        mapOf["changed"] = false
        userRepository
        .findById(changePassword.id!!)
        .ifPresent { item ->
            val dataPassword:String? = item.password
            if (bcry.matches(changePassword.old_password,dataPassword))
            {
                item.password = bcry.encode(changePassword.new_password)
                userRepository.save(item)
                mapOf["changed"] = true
            }
        }
        return mapOf
    }
}