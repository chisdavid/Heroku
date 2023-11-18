package com.ds.Assignement1.Assignement1.Dto;

import com.ds.Assignement1.Assignement1.Model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PersonResponseDTO {
    private Long id;
    private String name;
    private String address;
    private Date birthDate;
    private String email;
    private String phoneNumber;
    private List<DeviceCheckedDTO> devices;
    private Role role;
    private byte[] profilePicture;
}