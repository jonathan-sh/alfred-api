package org.ivfun.alfred.app.service.security.impl

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

/**
 * Created by: jonathan
 * DateTime: 2018-02-25 14:24
 **/
@EnableWebSecurity
class Request()
: WebSecurityConfigurerAdapter()
{
    override
    fun configure(http: HttpSecurity?)
    {
        http!!
        http
            .csrf()
            .disable()
            .exceptionHandling()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.POST, "/**").permitAll()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
    }
}