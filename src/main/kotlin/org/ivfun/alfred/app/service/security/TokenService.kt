package org.ivfun.som.security.service

import org.ivfun.alfred.app.document.User

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 18:54
 **/
interface TokenService
{
    fun generateToken(): Map<String, String>

    fun check(token: String): Boolean

    fun generateToken(user: User): Map<String, String>
}