package org.ivfun.alfred.app.document

import org.ivfun.alfred.app.usefull.enuns.UserLevel
import org.ivfun.mrt.validation.annotation.IsRequiredToCreate
import org.ivfun.mrt.validation.annotation.IsRequiredToUpdate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
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
    @IsRequiredToUpdate
    val name: String? = null,
    @IsRequiredToCreate
    @IsRequiredToUpdate
    @Indexed(unique = true)
    val email: String? = null,
    @IsRequiredToCreate
    @IsRequiredToUpdate
    var password: String? = null,
    val level: UserLevel? = UserLevel.DEVELOPMENT,
    val enable: Boolean? = true

)
{
    fun removePass() { password = null }
}