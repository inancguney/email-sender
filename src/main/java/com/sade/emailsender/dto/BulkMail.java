package com.sade.emailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BulkMail {
    public String subject;
    public String body;


    public List<String> to;
}
