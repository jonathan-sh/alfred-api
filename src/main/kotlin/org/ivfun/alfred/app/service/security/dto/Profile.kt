package org.ivfun.alfred.app.service.security.dto

import org.ivfun.alfred.app.document.User

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 22:04
 **/
data
class Profile
(
    val token:String? = null,
    val user:User? = User()
)
{
   init
   {
       user?.removePass()
   }
}