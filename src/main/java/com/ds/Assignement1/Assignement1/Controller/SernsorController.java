package com.ds.Assignement1.Assignement1.Controller;

import com.ds.Assignement1.Assignement1.Dto.AddSensorDTO;
import com.ds.Assignement1.Assignement1.Dto.AddSensorData;
import com.ds.Assignement1.Assignement1.Dto.UpdateDTO;
import com.ds.Assignement1.Assignement1.Service.Implementation.SensorServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.ds.Assignement1.Assignement1.Utils.Utils.genericResponse;

@CrossOrigin
@RequiredArgsConstructor
@Controller
@RequestMapping("/Sensor")
public class SernsorController {
    private final SensorServiceImplementation sensorServiceImplementation;

    @GetMapping("/GetAll")
    public ResponseEntity GetAll() {
        return genericResponse(sensorServiceImplementation.getAll());
    }

    @PostMapping ("/GetByClientId")
    public ResponseEntity GetByClientId(@RequestBody Long Id){
        return genericResponse(sensorServiceImplementation.getByClientId(Id));
    }

    @PostMapping("/Update")
    public ResponseEntity Update(@RequestBody UpdateDTO updateDTO) {
        return genericResponse(sensorServiceImplementation.update(updateDTO));
    }

    @PostMapping("/Insert")
    public ResponseEntity Insert(@RequestBody AddSensorDTO addSensorDTO) {
        return genericResponse(sensorServiceImplementation.insert(addSensorDTO));
    }

    @PostMapping("/Delete")
    public ResponseEntity Delete(@RequestBody List<Long> ids){
        return genericResponse(sensorServiceImplementation.delete(ids));
    }

    @PostMapping("/AddData")
    public ResponseEntity AddData (@RequestBody AddSensorData addSensorData){
        System.out.println(addSensorData);
        System.out.println(LocalDateTime.now().toString());
        return genericResponse(sensorServiceImplementation.addData(addSensorData));
    }
}