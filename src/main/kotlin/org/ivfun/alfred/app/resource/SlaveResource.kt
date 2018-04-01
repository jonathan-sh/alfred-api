package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.document.Slave
import org.ivfun.alfred.app.repository.SlaveRepository
import org.ivfun.alfred.app.service.slave.SlaveService
import org.ivfun.mrt.resource.GenericResource
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 10:18
 **/

@RestController
@RequestMapping(value = ["/slave"])
class SlaveResource(val slaveRepository: SlaveRepository,
                    val slaveService: SlaveService,
                    val responseTreatment: ResponseTreatment<Slave>)
: GenericResource<Slave>(slaveRepository, responseTreatment)
{
    @PostMapping
    override fun create(@RequestBody slave: Slave): ResponseEntity<Any> = slaveService.create(slave)

    @PutMapping
    override fun update(@RequestBody slave: Slave): ResponseEntity<Any> = slaveService.update(slave)
}