package com.sade.emailsender;


import com.sade.emailsender.dto.BulkMail;
import com.sade.emailsender.dto.CustomerInfo;
import com.sade.emailsender.dto.EmailTemplate;
import com.sade.emailsender.dto.PdfConverter;
import com.sade.emailsender.service.FileReader;
import com.sade.emailsender.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

        bulkMail.customerInfos.forEach(to -> {
            EmailTemplate emailTemplate = new EmailTemplate(to.mail, bulkMail.subject, bulkMail.body);
            String fileName = to.mail + ".pdf";
            fileReader.newPdf(fileReader,fileName);
            String yourContent = bulkMail.getBody().replace("%isim%", to.mail).replace("%salary%", to.salary);
            File pdfFile = fileReader.newPdf(fileReader,fileName);
            String htmlFilePath = "src/main/resources/template.html";
            String pdfFilePath = "src/main/resources/files/" + fileName;
            template.replace("%isim%", to.mail).replace("%maaş%", to.salary);
            PdfConverter pdfConverter = new PdfConverter();
            pdfConverter.convertHtmlToPdf(htmlFilePath, pdfFilePath);
            MimeMessage mimeMessage = sender.setSimpleMailMessage(emailTemplate, template, fileName, pdfFile);
            sender.sendHtmlMail(mimeMessage);

        });

    }
}

