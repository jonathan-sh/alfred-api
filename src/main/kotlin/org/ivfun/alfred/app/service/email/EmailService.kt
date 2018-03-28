package org.ivfun.alfred.app.service.email

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

/**
 * Created by: jonathan
 * DateTime: 2018-03-27 18:17
 **/
@Service
class EmailService(val javaMailSender: JavaMailSender)
{
    fun send(email: Email)
    {
        val message = SimpleMailMessage()
        message.setTo(email.to!!)
        message.setSubject(email.subject!!)
        message.setText(email.text!!)
        javaMailSender.send(message)
    }
}