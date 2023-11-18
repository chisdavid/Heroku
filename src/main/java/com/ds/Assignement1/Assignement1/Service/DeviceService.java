package com.ds.Assignement1.Assignement1.Service;

import com.ds.Assignement1.Assignement1.Dto.AddDeviceDTO;
import com.ds.Assignement1.Assignement1.Dto.UpdateDTO;
import com.ds.Assignement1.Assignement1.Model.Device;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface DeviceService {
    void update(Device device);
    void add(Device device);
    boolean delete (List<Long> ids);
    List<Device> getAll();
    boolean insert(AddDeviceDTO addDeviceDTO);
    boolean edit(UpdateDTO updateDTO);
}