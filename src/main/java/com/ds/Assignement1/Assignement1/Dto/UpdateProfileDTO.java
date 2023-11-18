package com.ds.Assignement1.Assignement1.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateProfileDTO {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String username;
    private String password;
    private Date birthDate;
}
