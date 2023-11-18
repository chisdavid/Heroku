package com.ds.Assignement1.Assignement1.Service;

import com.ds.Assignement1.Assignement1.Dto.AddSensorDTO;
import com.ds.Assignement1.Assignement1.Dto.AddSensorData;
import com.ds.Assignement1.Assignement1.Dto.UpdateDTO;

import com.ds.Assignement1.Assignement1.Model.Sensor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface SensorService {
    boolean update(UpdateDTO updateDTO);
    void add(Sensor sensor);
    boolean delete(List<Long> ids);
    List<Sensor> getAll();
    boolean insert(AddSensorDTO addSensorDTO);

    boolean addData(AddSensorData addSensorData);

    List<Sensor> getByClientId(Long id);
}