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
import javax.mail.internet.MimeMessage;
import java.io.File;


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
        String htmlTemplate = fileReader.readFile("template.html");
        String ektemplate = fileReader.readFile("files/index.html");
        BulkMail bulkMail = fileReader.stringToBulkMail(data);

        bulkMail.customerInfos.forEach(to -> {
            EmailTemplate emailTemplate = new EmailTemplate(to.mail, bulkMail.subject, bulkMail.body);
            String fileName = to.mail + ".pdf";
            String replacedEktemplate = String.format(ektemplate, to.name, to.raise, to.salary);
            File pdfFile = fileReader.htmlToPdf(fileName, replacedEktemplate);
            String replacedHtmlTemplate = htmlTemplate.replace("%isim%", to.mail).replace("%maaş%", to.salary);
            MimeMessage mimeMessage = sender.setSimpleMailMessage(emailTemplate, replacedHtmlTemplate, fileName, pdfFile);
            sender.sendHtmlMail(mimeMessage);
        });
    }
}

