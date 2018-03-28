package org.ivfun.alfred.app.document

import org.ivfun.mrt.validation.annotation.IsRequiredToCreate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 20:16
 **/
@Document(collection = "user")
data class User
(
    @Id
    val id: String? = null,
    @IsRequiredToCreate
    val name: String? = null,
    @IsRequiredToCreate
    val email: String? = null,
    @IsRequiredToCreate
    val password: String? = null,
    @IsRequiredToCreate
    val enable: Boolean? = false,
    @IsRequiredToCreate
    val admin: Boolean? = false
)