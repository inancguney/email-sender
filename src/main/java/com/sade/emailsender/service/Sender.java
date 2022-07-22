package com.sade.emailsender.service;

import com.sade.emailsender.dto.EmailTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

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

    public SimpleMailMessage setSimpleMailMessage(EmailTemplate bulkMail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(String.valueOf(bulkMail.to));
        simpleMailMessage.setText(bulkMail.body);
        simpleMailMessage.setSubject(bulkMail.subject);

        return simpleMailMessage;
    }

    @SneakyThrows
    public MimeMessage setMimeMailMessage(EmailTemplate emailTemplate,String template){
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(emailFrom);
        mimeMessageHelper.setTo(emailTemplate.to);
        mimeMessageHelper.setSubject(emailTemplate.subject);
        mimeMessageHelper.setText(template,true);
        return mimeMailMessage;
    }

    public void sendHtmlMail(MimeMessage mimeMessage){
        mailSender.send(mimeMessage);
        System.out.println("Mail sent succesfuly...");
    }

    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        mailSender.send(simpleMailMessage);
        System.out.println("Mail sent succesfuly...");
    }

}

