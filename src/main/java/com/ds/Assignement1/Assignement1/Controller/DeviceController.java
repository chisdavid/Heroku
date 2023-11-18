package com.ds.Assignement1.Assignement1.Controller;

import com.ds.Assignement1.Assignement1.Dto.AddDeviceDTO;
import com.ds.Assignement1.Assignement1.Dto.UpdateDTO;
import com.ds.Assignement1.Assignement1.Service.Implementation.DeviceServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.ds.Assignement1.Assignement1.Utils.Utils.genericResponse;

@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("/Device")
public class DeviceController {
    private final DeviceServiceImplementation deviceServiceImplementation;

    @GetMapping("/GetAll")
    public ResponseEntity GetAll() {
        return genericResponse(deviceServiceImplementation.getAll());
    }

    @PostMapping("/Add")
    public ResponseEntity Insert(@RequestBody AddDeviceDTO addDeviceDTO) {
        return genericResponse(deviceServiceImplementation.insert(addDeviceDTO));
    }

    @PostMapping("/Delete")
    public ResponseEntity Delete(@RequestBody List<Long> ids) {
        return genericResponse(deviceServiceImplementation.delete(ids));
    }

    @PostMapping("/Update")
    public ResponseEntity Update(@RequestBody UpdateDTO updateDTO) {
        return genericResponse(deviceServiceImplementation.edit(updateDTO));
    }
}