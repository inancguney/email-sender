package com.sade.emailsender.service;

import com.sade.emailsender.dto.EmailTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;

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


    @SneakyThrows
    public MimeMessage setSimpleMailMessage(EmailTemplate emailTemplate,String template, String fileName,File file){
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(emailFrom);
        mimeMessageHelper.setTo(emailTemplate.to);
        mimeMessageHelper.setSubject(emailTemplate.subject);
        mimeMessageHelper.addAttachment(fileName, file);
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

