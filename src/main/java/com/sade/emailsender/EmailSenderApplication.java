package com.sade.emailsender;

import com.sade.emailsender.dto.EmailTemplate;
import com.sade.emailsender.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;

import java.util.Scanner;

@SpringBootApplication
public class EmailSenderApplication {
    @Autowired
    private Sender sender;

    public static void main(String[] args) {

        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendmail() {
        EmailTemplate emailTemplate = new EmailTemplate("tamer.kacak@sadeyazilim.com",
                "EmailSender", "Merhaba, Email sender çalışıyor. Bu maili onunla yolluyorum.");

        SimpleMailMessage simpleMailMessage = sender.setSimpleMailMessage(emailTemplate);
        sender.sendEmail(simpleMailMessage);
    }
}
