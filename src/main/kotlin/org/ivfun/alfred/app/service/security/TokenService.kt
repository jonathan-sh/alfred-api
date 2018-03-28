package org.ivfun.alfred.app.service.security

import org.ivfun.alfred.app.document.User

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 18:54
 **/
interface TokenService
{
    fun generateToken(): String

    fun check(token: String): Boolean

    fun generateToken(user: User): String
}