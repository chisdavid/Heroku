package com.ds.Assignement1.Assignement1.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonRegisterDTO {
    private String address;
    private String name;
    private String username;
    private Date date;
    private String email;
    private String phoneNumber;
}