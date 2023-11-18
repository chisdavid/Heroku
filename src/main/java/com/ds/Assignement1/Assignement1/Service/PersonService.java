package com.ds.Assignement1.Assignement1.Service;

import com.ds.Assignement1.Assignement1.Dto.*;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Model.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Component
public interface PersonService {
    void update(UpdateDTO personUpdateDTO) throws ParseException, IOException;
    void delete(Person Person);
    void add(Person Person);
    Person getByRole(Role role);
    List<PersonResponseDTO> getAll();
    String register(PersonRegisterDTO clientRegisterDTO);
    boolean deleteByIds(List<Long> ids);
    String insert(AddPersonDTO addPersonDTO);
    boolean save(MultipartFile image);
    boolean editDevices(UpdateDevicesDTO updateDevicesDTO);

    Person uploadImage(MultipartFile multipartFile,Long id);

    Person getById(Long id);

    String updateProfile(UpdateProfileDTO updateProfileDTO);
}