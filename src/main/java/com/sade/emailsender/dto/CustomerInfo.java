package com.sade.emailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CustomerInfo {
    public String mail;
    public String salary;
}
