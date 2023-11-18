package com.ds.Assignement1.Assignement1.Service.Implementation;

import com.ds.Assignement1.Assignement1.Dto.AddDeviceDTO;
import com.ds.Assignement1.Assignement1.Dto.UpdateDTO;
import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Model.Sensor;
import com.ds.Assignement1.Assignement1.Repository.DeviceRepository;
import com.ds.Assignement1.Assignement1.Repository.PeopleRepository;
import com.ds.Assignement1.Assignement1.Repository.SensorRepository;
import com.ds.Assignement1.Assignement1.Service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImplementation implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final PeopleRepository peopleRepository;

    @Override
    public void update(Device device) {
        Device deviceDB = deviceRepository.findFirstById(device.getId());
        deviceDB.setAddress(device.getAddress());
        deviceDB.setDescription(device.getDescription());
        deviceDB.setAverageEnergyConsumption(device.getAverageEnergyConsumption());
        deviceDB.setSensor(device.getSensor());
        deviceDB.setMaximumEnergyConsumption(device.getMaximumEnergyConsumption());
    }

    @Override
    public void add(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public boolean delete(List<Long> ids) {
        if (ids == null) {
            return false;
        }

        for (Long i : ids) {
            Device device = deviceRepository.findFirstById(i);
            if (device!=null)
            {
                Person person = peopleRepository.findFirstByDevicesContains(device);
                if (person != null)
                {
                    person.getDevices().remove(device);
                }
            }
        }
        ids.stream().forEach(id -> deviceRepository.deleteById(id));
        return true;
    }

    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public boolean insert(AddDeviceDTO addDeviceDTO) {
        if (addDeviceDTO == null) {
            return false;
        }
        List<Sensor> sensors = sensorRepository.findAll();
        Device newDevice = Device.builder()
                .name(addDeviceDTO.getName())
                .description(addDeviceDTO.getDescription())
                .address(addDeviceDTO.getAddress())
                .maximumEnergyConsumption(addDeviceDTO.getMaximumEnergyConsumtion())
                .averageEnergyConsumption(addDeviceDTO.getAverageEnergyConsumtion())
                .sensor(sensors.get(addDeviceDTO.getSensorId().intValue()))
                .build();
        deviceRepository.save(newDevice);
        return true;
    }

    @Override
    @Transactional
    public boolean edit(UpdateDTO updateDTO) {
        if (updateDTO == null)
            return false;

        Device deviceBD = deviceRepository.findFirstById(updateDTO.getId());

        switch (updateDTO.getField()) {
            case "Name":
                deviceBD.setName(updateDTO.getValue().toString());
                break;
            case "Address":
                deviceBD.setAddress(updateDTO.getValue().toString());
                break;
            case "Description":
                deviceBD.setDescription(updateDTO.getValue().toString());
                break;
            case "MaximumEnergyConsumption":
                deviceBD.setMaximumEnergyConsumption(Long.parseLong(updateDTO.getValue().toString()));
                break;
            case "AverageEnergyConsumption":
                deviceBD.setAverageEnergyConsumption(Long.parseLong(updateDTO.getValue().toString()));
                break;
            case "Sensor":
                Sensor sensor = sensorRepository.findAll().stream().filter(i -> i.getName().equals(updateDTO.getValue().toString())).collect(Collectors.toList()).get(0);
                deviceBD.setSensor(sensor);
                break;
            default:
                break;
        }
        return true;
    }
}