package org.ivfun.alfred.app.document

import org.ivfun.alfred.app.usefull.enuns.GitHubWhStatus
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by: jonathan
 * DateTime: 2018-03-21 23:22
 **/
@Document(collection = "gitHubWebHook")
data class GitHubWh
(
    val ref: String? = null,
    val before: String? = null,
    val after: String? = null,
    val head_commit: GitHubWhHeadCommit? = null,
    val repository: GitHubWhRepository? = null,
    val sender: GitHubWhSender? = null,
    var status: GitHubWhStatus? = GitHubWhStatus.NOT_ACCEPTED
)




