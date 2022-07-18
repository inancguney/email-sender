package com.sade.emailsender;

import com.sade.emailsender.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EmailSenderApplication {
	@Autowired
	private Sender sender;

	public static void main(String[] args) {

		SpringApplication.run(EmailSenderApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void sendmail() {
		sender.sendEmail("tamer.kacak@sadeyazilim.com",
				"EmailSender", "Merhaba, Email sender çalışıyor. Bu maili onunla yolluyorum.");

	}
}
