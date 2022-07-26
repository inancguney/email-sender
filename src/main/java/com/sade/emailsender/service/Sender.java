package com.sade.emailsender.service;

import com.sade.emailsender.dto.EmailTemplate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

@Service
@Component
public class Sender {
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Get email from application.properties
     */
    @Value("${spring.mail.username}")
    private String emailFrom;

    public MimeMessage setSimpleMailMessage(EmailTemplate bulkMail) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(String.valueOf(bulkMail.to));
        simpleMailMessage.setText(bulkMail.body);
        simpleMailMessage.setSubject(bulkMail.subject);


        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(String.format(
                    simpleMailMessage.getText()));

            FileSystemResource file = new FileSystemResource("C:\\unnamed.png");
            helper.addAttachment(file.getFilename(), file);

        }catch (MessagingException e) {
            throw new MailParseException(e);
        }
        return message;
    }


    @SneakyThrows
   /* public MimeMessage setMimeMailMessage(EmailTemplate emailTemplate,String template){
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true,"UTF-8");
        mimeMessageHelper.setFrom(emailFrom);
        mimeMessageHelper.setTo(emailTemplate.to);
        mimeMessageHelper.addAttachment("main/resources/files");
        mimeMessageHelper.setSubject(emailTemplate.subject);
        mimeMessageHelper.setText(template,true);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);

        helper.setFrom(mimeMailMessage.getFrom());
        helper.setTo(mimeMailMessage.getTo());
        helper.setSubject(mimeMailMessage.getSubject());
        helper.setText(String.format(
                mimeMailMessage.getText(), dear, content));

        FileSystemResource file = new FileSystemResource("C:\\log.txt");
        helper.addAttachment(file.getFilename(), file);

    }catch (MessagingException e) {
        throw new MailParseException(e);
    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
        mailSender.send(message);
}
        return mimeMailMessage;
    }
    /*MimeMessage.body = new MimeBodyPart();
    String filename = "/main/resources/files/unnamed.png";
    DataSource source = new FileDataSource(filename);
         MimeMessage.body.setDataHandler(new DataHandler(source));
         MimeMessage.body.setFileName(filename);
*/



    public void sendHtmlMail(MimeMessage mimeMessage){
        mailSender.send(mimeMessage);
        System.out.println("Mail sent succesfuly...");
    }

    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        mailSender.send(simpleMailMessage);
        System.out.println("Mail sent succesfuly...");
    }

}

