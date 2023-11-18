package com.ds.Assignement1.Assignement1.Dto;

import com.ds.Assignement1.Assignement1.Model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceCheckedDTO {
    private Device device;
    private Boolean checked;
}