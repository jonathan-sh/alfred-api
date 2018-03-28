package org.ivfun.alfred.app.usefull.bash

import org.ivfun.alfred.app.document.Build

object Pipeline
{
    private const val build_sh: String = "src/main/resources/files/build.sh"
    private const val send_sh: String = "src/main/resources/files/send.sh"
    private const val deploy_sh: String = "alfred/bin/deploy.sh"

    fun build(build: Build)
    {
        try
        {
            val app_name: String = build.application?.name!!
            val app_type: String = build.application.type!!
            val branch: String = build.branch!!
            val build_id: String = build.id!!
            Shell.run("$build_sh $app_name $branch $app_type $build_id")
        }
        catch (e: Exception)
        {
            println("BUILD ERROR : build = " + build.id)
        }

    }

    fun send(build: Build): Boolean
    {
        return try
        {
            val key_file: String = build.slave?.key_file!!
            val root_path: String = build.slave.root_path!!
            val origin_file: String = build.artifact_file!!
            val user: String = build.slave.user!!
            val host_destiny: String = build.slave.ip!!
            Shell.run("$send_sh $key_file $origin_file $user $host_destiny $root_path")
            true
        }
        catch (e: Exception)
        {
            println("SEND ERROR : build = " + build.id)
            false
        }
    }

    fun deploy(build: Build)
    {
        try
        {
            val key_file: String = build.slave?.key_file!!
            val root_path: String = build.slave.root_path!!
            val user: String = build.slave.user!!
            val host_destiny: String = build.slave.ip!!
            val app_type: String = build.application?.type!!
            val app_name: String = build.application.name!!
            val branch: String = build.branch!!
            val service: String = when (build.application.type)
            {
                "jar" -> "$app_name.security"
                "war" -> "tomcat8"
                "js" -> "apache2"
                else -> "SERVICE NOT SPECIFIED"
            }
            Shell.run("ssh -i $key_file $user@$host_destiny ${Pipeline.deploy_sh} $app_type $app_name $branch $root_path $service ")
        }
        catch (e: Exception)
        {
            println("IN_DEPLOY ERROR : build = " + build.id)
        }


    }

}