package com.ds.Assignement1.Assignement1.Repository;

import com.ds.Assignement1.Assignement1.Model.Sensor;
import com.ds.Assignement1.Assignement1.Service.SensorService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long> {
    Sensor findFirstById(Long id);
    Sensor findFirstByName(String name);
    List<Sensor> findAll();
}