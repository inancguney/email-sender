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
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.thymeleaf.model.IText;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


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
    public void sendmail() {

        String data = fileReader.readFile("mail_otosend.json");
        String template = fileReader.readFile("template.html");
        BulkMail bulkMail = fileReader.stringToBulkMail(data);


        bulkMail.to.forEach(to -> {
            EmailTemplate emailTemplate = new EmailTemplate(to, bulkMail.subject, bulkMail.body);
            String fileName = to + ".txt";
            String yourContent = bulkMail.getBody().replace("%isim%", to);
            File file = new File("src/main/resources/files/" + fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    fileReader.addToFile(file, yourContent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }


            MimeMessage mimeMessage = sender.setSimpleMailMessage(emailTemplate, template.replace("%isim%", to), fileName, file);
            sender.sendHtmlMail(mimeMessage);

        });

    }
}

