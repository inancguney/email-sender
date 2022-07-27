package com.sade.emailsender;



import com.sade.emailsender.dto.BulkMail;
import com.sade.emailsender.dto.EmailTemplate;
import com.sade.emailsender.service.FileReader;
import com.sade.emailsender.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.model.IText;

import javax.mail.internet.MimeMessage;


@SpringBootApplication
public class EmailSenderApplication {
    @Autowired
    private Sender sender;
    @Autowired
    private FileReader fileReader;

    public static void main(String[] args) {

        SpringApplication.run(EmailSenderApplication.class, args);
    }

    /**
     * forEach turns every email template in emailTemplates list. And it sends every email message step by step.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void sendmail(){

        String data = fileReader.readFile("mail_otosend.json");
        String template = fileReader.readFile("template.html");
        BulkMail bulkMail = fileReader.stringToBulkMail(data);

        /*
        * to = gönderdiğin mail
        * fileName'e gönderdiğin maili yazman gerekiyor
        * Not= Sonunda txt var.
        *
        *
        *
        * */

        bulkMail.to.forEach(to -> {
            bulkMail.file =  bulkMail.getTo() + ".txt";
            EmailTemplate emailTemplate = new EmailTemplate(to, bulkMail.subject, bulkMail.body, bulkMail.file);
             MimeMessage mimeMessage = sender.setSimpleMailMessage(emailTemplate, template.replace("%isim%",to), bulkMail.file);
            sender.sendHtmlMail(mimeMessage);
        });
        /*
         * TODO: read file from mail_otosend.json
         *  send email*/
        }
    }

