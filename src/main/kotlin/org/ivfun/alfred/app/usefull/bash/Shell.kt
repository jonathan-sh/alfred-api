package org.ivfun.alfred.app.usefull.bash

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * Created by: jonathan
 * DateTime: 2018-03-20 18:11
 **/
object Shell
{
    fun run(command: String) = bash(command)

    fun run(command: List<String>) = command.forEach { c -> bash(c) }

    private fun bash(command: String)
    {
        val commands = ArrayList<String>()
        commands.add("/bin/bash")
        commands.add("-c")
        commands.add(command)

        try
        {
            val p = ProcessBuilder(commands)
            val process = p.start()
            val input: InputStream = process.inputStream
            val inputReader = InputStreamReader(input)
            val bufferedReader = BufferedReader(inputReader)
            bufferedReader.lines().forEach { l -> println(l) }

        }
        catch (ioe: IOException)
        {
            throw ioe
        }
    }

}