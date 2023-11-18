package com.ds.Assignement1.Assignement1.Dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class AddSensorData {
    private Long sensorId;
    private LocalDateTime date;
    private Double value;
}
