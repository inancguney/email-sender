package com.sade.emailsender.service;

import com.sade.emailsender.dto.BulkMail;
import com.sade.emailsender.dto.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class Sender {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Get email from application.properties
     */
    @Value("${spring.mail.username}")
    private String emailFrom;

    public SimpleMailMessage setSimpleMailMessage(EmailTemplate emailTemplate) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(String.valueOf(emailTemplate.to));
        simpleMailMessage.setText(emailTemplate.body);
        simpleMailMessage.setSubject(emailTemplate.subject);

        return simpleMailMessage;
    }

    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        mailSender.send(simpleMailMessage);
        System.out.println("Mail sent succesfuly...");
    }

}

