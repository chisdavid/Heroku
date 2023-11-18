package com.ds.Assignement1.Assignement1.Dto;

import com.ds.Assignement1.Assignement1.Dto.PersonResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String jwt;
    private PersonResponseDTO Person;
}