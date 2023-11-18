package com.ds.Assignement1.Assignement1.Dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class UpdateDevicesDTO {
    private Long id;
    private ArrayList<String> devices;
}