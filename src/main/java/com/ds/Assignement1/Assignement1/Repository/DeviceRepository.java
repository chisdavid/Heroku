package com.ds.Assignement1.Assignement1.Repository;


import com.ds.Assignement1.Assignement1.Model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
    List<Device> findAll();
    Device findFirstById(Long id);
    Device findFirstByName(String name);
    Device findFirstBySensorId(Long id);
}