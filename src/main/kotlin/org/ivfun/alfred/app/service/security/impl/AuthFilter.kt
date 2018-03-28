package org.ivfun.alfred.app.service.security.impl

import org.ivfun.alfred.app.usefull.AppConstant
import org.ivfun.som.security.service.TokenService
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 14:16
 **/
@EnableWebSecurity
class AuthFilter(private val tokenService: TokenService,
                 private val appConstant: AppConstant) : OncePerRequestFilter()
{
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain)
    {

        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Content-Encoding, " + appConstant.TOKEN_HEADER)

        val authToken:String? = request.getHeader(appConstant.TOKEN_HEADER)

        if (authToken!=null && tokenService.check(authToken))
        {
            filterChain.doFilter(request, response)
        }
        else
        {
            filterChain.doFilter(request, response)
            //response.sendError(401)
        }

    }
}