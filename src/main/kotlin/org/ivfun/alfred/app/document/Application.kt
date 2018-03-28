package org.ivfun.alfred.app.document


import org.ivfun.mrt.validation.annotation.IsRequiredToCreate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by: jonathan
 * DateTime: 2018-03-21 23:00
 **/

@Document(collection = "application")
data class Application
(
     @Id
     val id: String? = null,
     @IsRequiredToCreate
     val name: String? = null,
     @IsRequiredToCreate
     val type: String? = null,
     val enable: Boolean? = true
)
