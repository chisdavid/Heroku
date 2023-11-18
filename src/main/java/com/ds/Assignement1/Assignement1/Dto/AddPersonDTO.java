package com.ds.Assignement1.Assignement1.Dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class AddPersonDTO {
    private String name;
    private String username;
    private String userType;
    private String email;
    private String phone;
    private Date date;
    private String address;
    private List<String> devices;
}