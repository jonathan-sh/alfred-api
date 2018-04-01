package org.ivfun.alfred.app.service.slave.impl

import org.ivfun.alfred.app.document.Slave
import org.ivfun.alfred.app.repository.SlaveRepository
import org.ivfun.alfred.app.service.slave.SlaveService
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Created by: jonathan
 * DateTime: 2018-03-28 11:23
 **/
@Service
class SlaveServiceImpl(private val slaveRepository: SlaveRepository,
                       private val responseTreatment: ResponseTreatment<Slave>)
:SlaveService
{
    override
    fun create(slave: Slave): ResponseEntity<Any>
    {
        if (alreadyExistsToCreate(slave))
        {
            return duplicateKey(slave)
        }
        return responseTreatment.create(slaveRepository,slave)
    }

    override
    fun update(slave: Slave): ResponseEntity<Any>
    {
        if (alreadyExistsToUpdade(slave))
        {
            return duplicateKey(slave)
        }
        return responseTreatment.update(slaveRepository,slave)
    }


    private fun alreadyExistsToCreate(slave: Slave) :Boolean
    {
        return slave.ip != null && slaveRepository.findByIp(slave.ip).isPresent
    }

    private fun alreadyExistsToUpdade(slave: Slave) :Boolean
    {
        var already :Boolean = false
        if(slave.ip != null && slave.id != null)
        {
            slaveRepository.findByIp(slave.ip)
                           .ifPresent { found -> already = slave.id != found.id }
        }
        return already
    }

    private fun duplicateKey(slave: Slave): ResponseEntity<Any>
    {
        val mapOf = mapOf("cause" to "duplicate key [ip] < " + slave.ip + " >")
        return ResponseEntity<Any>(mapOf,HttpStatus.NOT_ACCEPTABLE)
    }
}