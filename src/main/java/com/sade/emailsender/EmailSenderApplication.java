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


import java.util.List;


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

        String data = fileReader.readFileFrom_maildetail();
        BulkMail bulkMail = fileReader.stringToBulkMail(data);

        bulkMail.to.forEach(to -> {
            EmailTemplate emailTemplate = new EmailTemplate(to, bulkMail.subject, bulkMail.body.replace("%isim%", to));
            SimpleMailMessage simpleMailMessage = sender.setSimpleMailMessage(emailTemplate);
            sender.sendEmail(simpleMailMessage);
        });


        /*
         * TODO: read file from mail_otosend.json
         *  send email*/
    }
}

