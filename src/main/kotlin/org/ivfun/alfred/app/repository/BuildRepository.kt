package org.ivfun.alfred.app.repository

import org.ivfun.alfred.app.document.Build
import org.ivfun.alfred.app.usefull.enuns.BuildStatus
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:06
 **/
interface BuildRepository : MongoRepository<Build, String>
{
    fun findByStatus(buildStatus: BuildStatus): MutableList<Build>
}