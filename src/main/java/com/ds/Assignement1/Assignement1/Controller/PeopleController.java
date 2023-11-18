package com.ds.Assignement1.Assignement1.Controller;

import com.ds.Assignement1.Assignement1.Dto.*;
import com.ds.Assignement1.Assignement1.Service.Implementation.PeopleServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ds.Assignement1.Assignement1.Utils.Utils.genericResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/Client")
@CrossOrigin

public class PeopleController {
    private final PeopleServiceImplementation peopleServiceImplementation;

    @GetMapping("/GetAll")
    public ResponseEntity GetAll() {
        return genericResponse(peopleServiceImplementation.getAll());
    }

    @PostMapping("/GetById")
    public ResponseEntity GetById(@RequestBody Long id) {
        return genericResponse(peopleServiceImplementation.getById(id));
    }

    @PostMapping("/Register")
    public ResponseEntity Register(@RequestBody PersonRegisterDTO clientRegisterDTO) {
        System.out.println(clientRegisterDTO);
        return genericResponse(peopleServiceImplementation.register(clientRegisterDTO));
    }

    @PostMapping("/Update")
    public void Update(@RequestBody UpdateDTO personUpdateDTO) {
        peopleServiceImplementation.update(personUpdateDTO);
    }

    @PostMapping("/Delete")
    public ResponseEntity Delete(@RequestBody List<Long> ids) {
        return genericResponse(peopleServiceImplementation.deleteByIds(ids));
    }

    @PostMapping("/Insert")
    public ResponseEntity Insert(@RequestBody AddPersonDTO addPersonDTO) {
        return genericResponse(peopleServiceImplementation.insert(addPersonDTO));
    }

    @PostMapping("/UpdateDevice")
    public ResponseEntity UpdateDevice(@RequestBody UpdateDevicesDTO updateDevicesDTO) {
        return genericResponse(peopleServiceImplementation.editDevices(updateDevicesDTO));
    }

    @PostMapping(value = "/UploadImage", consumes = {"multipart/form-data"})
    public ResponseEntity UploadImage(@RequestParam(value = "image", required = false) MultipartFile multipartFile, @RequestParam(value = "clientId") String clientID) {
        return genericResponse(peopleServiceImplementation.uploadImage(multipartFile, Long.parseLong(clientID)));
    }

    @PostMapping(value = "/UpdateProfile")
    public void UpdateProfile (@RequestBody UpdateProfileDTO updateProfileDTO){
        genericResponse(peopleServiceImplementation.updateProfile(updateProfileDTO));
    }
}