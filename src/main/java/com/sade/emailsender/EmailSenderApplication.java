package com.sade.emailsender;


import com.sade.emailsender.dto.BulkMail;
import com.sade.emailsender.dto.CustomerInfo;
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

        String data = fileReader.readFile("mail_otosend.json");
        String template = fileReader.readFile("template.html");
        BulkMail bulkMail = fileReader.stringToBulkMail(data);
        //List<CustomerInfo> tol =  bulkMail.getToList();

            EmailTemplate emailTemplate = new EmailTemplate(CustomerInfo.mail, bulkMail.subject, bulkMail.body);
            String fileName = CustomerInfo.mail + ".txt";
            String yourContent = bulkMail.getBody().replace("%isim%", CustomerInfo.mail).replace("%salary%", CustomerInfo.salary);
            File file = fileReader.newFile(fileReader, fileName, yourContent);
            MimeMessage mimeMessage = sender.setSimpleMailMessage(emailTemplate, template.replace("%isim%", CustomerInfo.mail).replace("%maaş%", CustomerInfo.salary), fileName, file);
            sender.sendHtmlMail(mimeMessage);




    }
}

