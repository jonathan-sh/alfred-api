package org.ivfun.alfred.app.repository

import org.ivfun.alfred.app.document.Application
import org.ivfun.alfred.app.document.User
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:06
 **/
interface UserRepository : MongoRepository<User, String>
{

}