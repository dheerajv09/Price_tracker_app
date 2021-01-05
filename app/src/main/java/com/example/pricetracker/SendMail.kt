package com.example.pricetracker

import android.os.AsyncTask
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class SendMail(private val email:String,private val subject:String ,private val message:String): AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        println("-----------doInBackground")
        val properties = Properties()

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        //Creating a new session
        val session = Session.getDefaultInstance(properties,object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD)
            }
        })

        try {
            //Creating MimeMessage object
            val mimeMessage = MimeMessage(session)

            //Setting sender address
            mimeMessage.setFrom(InternetAddress(Utils.EMAIL))
            //Adding receiver
            mimeMessage.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            //Adding subject
            mimeMessage.subject = subject
            //Adding message
            mimeMessage.setText(message)
            //Sending email
            Transport.send(mimeMessage)

        } catch (e: MessagingException) {
            println("-----------error")
            println("-----------${e}")
            e.printStackTrace()
        }

        return null
    }


    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
    }

    override fun onCancelled() {
        super.onCancelled()
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}