package org.robogit.utils

import org.robogit.dto.CardElementDto
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.Properties

internal class MailSender {
    val username = "bandoffourmail@gmail.com"
    val password = PASSWORD

    fun sendMail(to: String, subject: String, body: String): Boolean {
        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(username, password)
                    }
                })

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress("bandoffourmail@gmail.com"))
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to))
            message.subject = subject
            message.setText(body)

            Transport.send(message)

        } catch (e: MessagingException) {
            return false
        }
        return true
    }

    fun createOrderMessage(card: List<CardElementDto>): String {
        if (card.isEmpty()){
            return "Мы будем рады видеть Вас на Robogit :)"
        }
        var res = "Вы совершили заказ:\n\n"
        var sum = 0f
        for (item in card){
            res += "Название товара: " + item.information.name + "\n"
            res += "Кол-во: " + item.amount + "\n"
            res += "Цена за единицу: " + item.information.price + "\n\n"
            sum += item.amount * item.information.price!!
        }
        res += "\nИтог: " + sum
        return res
    }

}