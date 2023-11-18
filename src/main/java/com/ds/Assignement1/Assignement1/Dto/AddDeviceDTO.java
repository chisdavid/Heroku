package com.ds.Assignement1.Assignement1.Dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AddDeviceDTO {
    private String name;
    private String address;
    private String description;
    private Long maximumEnergyConsumtion;
    private Long averageEnergyConsumtion;
    private Long sensorId;
}