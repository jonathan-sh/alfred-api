package org.ivfun.alfred.app.repository

import org.ivfun.alfred.app.document.Slave
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:06
 **/
interface SlaveRepository : MongoRepository<Slave, String>
{
    fun findByIp(ip:String):Optional<Slave>

}