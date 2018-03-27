package org.ivfun.alfred.app.resource

import org.ivfun.alfred.app.document.Slave
import org.ivfun.alfred.app.repository.SlaveRepository
import org.ivfun.mrt.resource.GenericResource
import org.ivfun.mrt.response.ResponseTreatment
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by: jonathan
 * DateTime: 2018-03-22 10:18
 **/

@RestController
@RequestMapping(value = ["/slave"])
class SlaveResource(slaveRepository: SlaveRepository,
                    responseTreatment: ResponseTreatment<Slave>)
    : GenericResource<Slave>(slaveRepository, responseTreatment)


