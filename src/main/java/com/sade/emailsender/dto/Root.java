package com.sade.emailsender.dto;
import java.util.ArrayList;

public class Root {

    public String subject;
    public String body;
    public String file;
    public ArrayList<To> myto;


    public static class To {
        public static String mail;
        public static String salary;

    }
}

