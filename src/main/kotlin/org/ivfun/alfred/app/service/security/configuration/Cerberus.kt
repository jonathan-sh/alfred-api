package org.ivfun.alfred.app.service.security.configuration

import org.ivfun.alfred.app.service.security.TokenService
import org.ivfun.alfred.app.usefull.AppConstant
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class Cerberus(private val tokenService: TokenService)
{

    fun allow(request: HttpServletRequest): Boolean
    {
        return when
        {
            isOpenMetothd(request) -> true
            isOpenUri(request) -> true
            this.isAuthorized(request) -> true
            else -> false
        }

    }

    private fun isOpenMetothd(request: HttpServletRequest): Boolean
    {
        return request.method.toString() == "OPTIONS"
    }

    private val OPEN_URI = listOf("/auth","/build","/wh-git-hub","/auth/create-first-admin")
    private fun isOpenUri(request: HttpServletRequest): Boolean
    {
        val uri:String = request.servletPath
        return OPEN_URI.contains(uri)

    }

    private fun isAuthorized(request: HttpServletRequest): Boolean
    {
        val authToken:String? = request.getHeader(AppConstant.TOKEN_HEADER)
        return  authToken != null && tokenService.check(authToken)
    }
}