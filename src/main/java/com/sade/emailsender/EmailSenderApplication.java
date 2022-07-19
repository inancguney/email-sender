package com.sade.emailsender;


import com.sade.emailsender.dto.EmailTemplate;
import com.sade.emailsender.service.FileReader;
import com.sade.emailsender.service.JSONObject;
import com.sade.emailsender.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;


@SpringBootApplication
public class EmailSenderApplication {
    @Autowired
    private Sender sender;
    @Autowired
    private FileReader fileReader;

    public static void main(String[] args) {

        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)


        public void sendmail() {
        fileReader.readFile();
        String new_data = fileReader.toString();
        JSONObject obj = new JSONObject(new_data);
        String n = obj.getString("to");
        String m = obj.getString("body");
        String k = obj.getString("subject");


        EmailTemplate emailTemplate = new EmailTemplate("n", "k", "m");

        SimpleMailMessage simpleMailMessage = sender.setSimpleMailMessage(emailTemplate);
        sender.sendEmail(simpleMailMessage);

    }
}
