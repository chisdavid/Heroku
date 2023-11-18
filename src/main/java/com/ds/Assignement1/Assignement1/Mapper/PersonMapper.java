package com.ds.Assignement1.Assignement1.Mapper;


import com.ds.Assignement1.Assignement1.Dto.DeviceCheckedDTO;
import com.ds.Assignement1.Assignement1.Dto.PersonResponseDTO;
import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonMapper {

    public static PersonResponseDTO mapToPersonResponse (Person person, List<Device> deviceList) {

        return PersonResponseDTO.builder().name(person.getName())
                .address(person.getAddress())
                .id(person.getId())
                .birthDate(person.getBirthDate())
                .email(person.getEmail())
                .profilePicture(person.getProfilePicture())
                .role(person.getRole())
                .devices(deviceList.stream().map(device -> person.getDevices().contains(device) ? new DeviceCheckedDTO(device,true) : new DeviceCheckedDTO(device,false)).collect(Collectors.toList()))
                .phoneNumber(person.getPhoneNumber())
                .build();
    }
}