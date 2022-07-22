package com.sade.emailsender.service;

import com.google.gson.Gson;
import com.sade.emailsender.dto.BulkMail;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
@Component
public class FileReader {
    public String readFile(String path) {
        try {

            FileInputStream fis = new FileInputStream(String.format("src/main/resources/%s", path));
            String data = IOUtils.toString(fis, "UTF-8");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument");
        }
    }

    //TODO: readFileFrom_mailotosend

    /**
     * This method converts string data to email templates as list.
     *
     * @param data it is data from mail_detail.json which is string.
     * @return it is converted email templates as list.
     */
    /*public List<EmailTemplate> stringToEmailTemplates(String data) {
        Type listType = new TypeToken<ArrayList<EmailTemplate>>() {
        }.getType();
        List<EmailTemplate> emailTemplates = new Gson().fromJson(data, listType);
        return emailTemplates;
    }*/
    public BulkMail stringToBulkMail(String data) {
        BulkMail bulkMails = new Gson().fromJson(data, BulkMail.class);
        return bulkMails;
    }


    // TODO: stringToBulkMail

}
