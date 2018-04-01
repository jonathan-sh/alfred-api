package org.ivfun.alfred.app.service.slave

import org.ivfun.alfred.app.document.Slave
import org.springframework.http.ResponseEntity

/**
 * Created by: jonathan
 * DateTime: 2018-03-28 15:32
 **/
interface SlaveService
{
    fun create(slave: Slave): ResponseEntity<Any>
    fun update(slave: Slave): ResponseEntity<Any>
}