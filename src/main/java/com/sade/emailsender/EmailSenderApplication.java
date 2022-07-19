package com.sade.emailsender;


import com.google.gson.Gson;
import com.sade.emailsender.dto.EmailTemplate;
import com.sade.emailsender.service.FileReader;
import com.sade.emailsender.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;

import java.util.ArrayList;
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

    @EventListener(ApplicationReadyEvent.class)
    public void sendmail() {
        String jsonString = fileReader.readFile();
        Gson g = new Gson();
        ArrayList<EmailTemplate> emailTemplates = g.fromJson(jsonString, ArrayList.class);


    }
}
