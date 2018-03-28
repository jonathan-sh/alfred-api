package org.ivfun.alfred

import org.ivfun.mrt.MrtApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 21:35
 **/
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(AlfredApplication::class,MrtApplication::class))
class AlfredApplicationTests
{

    @Test
    fun contextLoads()
    {
    }

}