package com.sade.emailsender.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;

@Service
@Component
public class FileReader {
    public String readFile() {
        try {

            FileInputStream fis = new FileInputStream("src/main/resources/mail_detail.json");
            String data = IOUtils.toString(fis, "UTF-8");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument");
        }
    }
}
