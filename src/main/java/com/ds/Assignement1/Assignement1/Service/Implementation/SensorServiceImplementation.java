package com.ds.Assignement1.Assignement1.Service.Implementation;

import com.ds.Assignement1.Assignement1.Dto.AddSensorDTO;
import com.ds.Assignement1.Assignement1.Dto.AddSensorData;
import com.ds.Assignement1.Assignement1.Dto.UpdateDTO;
import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Model.Sensor;
import com.ds.Assignement1.Assignement1.Model.SensorData;
import com.ds.Assignement1.Assignement1.Repository.DeviceRepository;
import com.ds.Assignement1.Assignement1.Repository.PeopleRepository;
import com.ds.Assignement1.Assignement1.Repository.SensorRepository;
import com.ds.Assignement1.Assignement1.Service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SensorServiceImplementation implements SensorService {
    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;
    private final PeopleRepository peopleRepository;
    private final SoketServiceImplementation soketServiceImplementation;

    @Override
    @Transactional
    public boolean update(UpdateDTO updateDTO) {
        Sensor sensorDB = sensorRepository.findFirstById(updateDTO.getId());

        if (sensorDB == null || updateDTO == null)
            return false;

        switch (updateDTO.getField()) {
            case "Name":
                sensorDB.setName(updateDTO.getValue().toString());
                break;
            case "Description":
                sensorDB.setDescription(updateDTO.getValue().toString());
                break;
            case "MaxValue":
                sensorDB.setMaxValue(new Long(updateDTO.getValue().toString()));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void add(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    @Override
    public boolean delete(List<Long> ids) {
        if (ids == null)
            return false;
        ids.stream().map(i -> deviceRepository.findFirstBySensorId(i)).forEach(j -> j.setSensor(null));
        ids.stream().forEach(id -> sensorRepository.deleteById(id));
        return true;
    }

    @Override
    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    @Override
    public boolean insert(AddSensorDTO addSensorDTO) {
        if (addSensorDTO == null) {
            return false;
        }
        sensorRepository.save(Sensor.builder().name(addSensorDTO.getName()).description(addSensorDTO.getDescription()).maxValue(addSensorDTO.getMaxValue()).build());
        return true;
    }

    private String verifySendSocket(AddSensorData addSensorDataFinal, SensorData addSensorDataInitial, Long maxValue) {
        Duration duration = Duration.between(addSensorDataInitial.getDate(), addSensorDataFinal.getDate());

        if ((addSensorDataFinal.getValue() - addSensorDataInitial.getValue()) / (new Long(duration.getSeconds()) / 3600.0) > maxValue)
            return (addSensorDataFinal.getValue() - addSensorDataInitial.getValue()) / (new Long(duration.getSeconds()) / 3600.0) +" > " +maxValue;
        return "";
    }

    @Override
    @Transactional
    public boolean addData(AddSensorData addSensorData) {
        Device device = deviceRepository.findFirstBySensorId(addSensorData.getSensorId());
        Person person = null;
        if (device != null) {
            person = peopleRepository.findFirstByDevicesContains(device);
        }

        if (person == null)
        {
            return false;
        }

        Sensor sensorDB = sensorRepository.findFirstById(addSensorData.getSensorId());
        if (sensorDB == null)
        {
            return false;
        }
        if ( sensorDB.getDataList().size() == 0) {
            sensorDB.getDataList().add(SensorData.builder().date(addSensorData.getDate()).value(addSensorData.getValue()).build());
            return false;
        }

        List<SensorData> oldData = sensorDB.getDataList();
        if (oldData == null) {
            sensorDB.setDataList(new ArrayList<SensorData>());
        }
        String message  = verifySendSocket(addSensorData, sensorDB.getDataList().get(sensorDB.getDataList().size() - 1), sensorDB.getMaxValue());

        if (message!="") {
            soketServiceImplementation.SendMessage(person.getId(), person.getName() +" "+message);
        }
        oldData.add(SensorData.builder()
                .date(addSensorData.getDate())
                .value(addSensorData.getValue() )
                .build());

        return true;
    }

    @Override
    public List<Sensor> getByClientId(Long id) {
        Person person = peopleRepository.findFirstById(id);
        if (person != null) {
            return person.getDevices().stream().map(i -> i.getSensor()).collect(Collectors.toList());
        }
        return null;
    }
}