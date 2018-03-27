package org.ivfun.alfred.app.repository

import org.ivfun.alfred.app.document.GitHubWh
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 00:06
 **/
interface GitHubWhRepository : MongoRepository<GitHubWh, String>