package com.sade.emailsender.service;

import org.apache.logging.log4j.message.Message;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.apache.logging.log4j.message.Message.*;

public class SendHtmlEmail {
        public static void main(String[] args) {
            // Recipient's email ID needs to be mentioned.
            String to = "medetnar@gmail.com";

            // Sender's email ID needs to be mentioned
            String from = "hinancguney@gmail.com";
            final String username = "hinancguney@gmail.com";//change accordingly
            final String password = "xqkhwygbmhgevesk";//change accordingly

            // Assuming you are sending email through relay.jangosmtp.net
            String host = "relay.jangosmtp.net";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "25");

            // Get the Session object.
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                // Create a default MimeMessage object.
                Message message = (Message) new MimeMessage(session);

                // Set From: header field of the header.
                ((MimeMessage) message).setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                ((MimeMessage) message).setRecipients(MimeMessage.RecipientType.TO,
                        InternetAddress.parse(to));

                // Set Subject: header field
                ((MimeMessage) message).setSubject("Testing Subject");

                // Send the actual HTML message, as big as you like
                ((MimeMessage) message).setContent(
                        "<h1>This is actual message embedded in HTML tags</h1>",
                        "text/html");

                // Send message
                Transport.send((javax.mail.Message) message);

                System.out.println("Sent message successfully....");

            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
