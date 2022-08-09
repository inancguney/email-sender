package com.sade.emailsender.service;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sade.emailsender.dto.BulkMail;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

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

    //TODO: yeni method
    public void addToFile(File file, String yourContent) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(yourContent);
        fileWriter.close();

    }


    public File newFile(FileReader fileReader, String fileName, String yourContent) {
        File file = new File("src/main/resources/files/" + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileReader.addToFile(file, yourContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }


    public File newPdf(FileReader fileReader, String fileName, String yourContent) {
        File file = new File("src/main/resources/files/" + fileName);
        if (!file.exists()) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/files/" + fileName));

                document.open();
                document.add(new Paragraph(yourContent));
                document.close();
                file = new File("src/main/resources/files/" + fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }


    /**
     * This method converts string data to email templates as list.
     *
     * @param data it is data from mail_otosend.json which is string.
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
