package org.ivfun.alfred.app.repository

import org.ivfun.alfred.app.document.User
import org.ivfun.alfred.app.usefull.enuns.UserLevel
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:06
 **/
interface UserRepository : MongoRepository<User, String>
{
    fun findByName(name:String):Optional<User>

    fun findByEmail(email:String):Optional<User>

    fun findByLevel(level:UserLevel):MutableList<User>
}