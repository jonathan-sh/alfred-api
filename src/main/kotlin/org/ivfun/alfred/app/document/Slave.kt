package org.ivfun.alfred.app.document

import org.ivfun.alfred.app.usefull.enuns.SlaveLevel
import org.ivfun.mrt.validation.annotation.IsRequiredToCreate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by: jonathan
 * DateTime: 2018-03-21 22:37
 **/
@Document(collection = "slave")
data class Slave
(
        @Id
        val id: String? = null,
        @IsRequiredToCreate
        val name: String? = null,
        @IsRequiredToCreate
        val user: String? = null,
        @IsRequiredToCreate
        val ip: String? = null,
        @IsRequiredToCreate
        val key_file: String? = null,
        @IsRequiredToCreate
        val root_path: String? = null,
        val branches: List<String>? = listOf(),
        val applications: List<String>? = listOf(),
        val level: SlaveLevel? = SlaveLevel.PRODUCTION,
        val enable: Boolean? = true
)