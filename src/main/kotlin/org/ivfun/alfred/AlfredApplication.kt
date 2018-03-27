package org.ivfun.alfred

import org.ivfun.mrt.MrtApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class AlfredApplication

fun main(args: Array<String>)
{
    val springApplication = SpringApplication()
    springApplication.addPrimarySources(listOf(AlfredApplication::class.java, MrtApplication::class.java))
    springApplication.run(*args)
}


