package org.ivfun.alfred.app.service.email

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 18:26
 **/
data class Email
(
    var to:String? = null,
    var subject:String? = null,
    var text:String? = null
)