package org.ivfun.alfred.app.service.security.impl


import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthServiceImplTest
{
    @Test
    fun validEncodeOK(){
        val bCrypt = BCryptPasswordEncoder()
        val encode = bCrypt.encode("123456")
        println(bCrypt.matches("123456",encode))
    }
}