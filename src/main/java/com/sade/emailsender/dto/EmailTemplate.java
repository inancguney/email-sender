package com.sade.emailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class EmailTemplate {

    public String to;
    public String subject;
    public String body;
}