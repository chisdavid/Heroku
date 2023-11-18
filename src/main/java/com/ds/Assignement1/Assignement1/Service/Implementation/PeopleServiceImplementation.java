package com.ds.Assignement1.Assignement1.Service.Implementation;

import com.ds.Assignement1.Assignement1.Dto.*;
import com.ds.Assignement1.Assignement1.Enums.UserType;
import com.ds.Assignement1.Assignement1.Mapper.PersonMapper;
import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Model.Role;
import com.ds.Assignement1.Assignement1.Model.Status;
import com.ds.Assignement1.Assignement1.Repository.DeviceRepository;
import com.ds.Assignement1.Assignement1.Repository.PeopleRepository;
import com.ds.Assignement1.Assignement1.Repository.RoleRepository;
import com.ds.Assignement1.Assignement1.Service.PersonService;
import com.ds.Assignement1.Assignement1.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ds.Assignement1.Assignement1.Utils.Constants.PASSWORD_LENGTH;
import static com.ds.Assignement1.Assignement1.Utils.Utils.generatePassword;
import static com.ds.Assignement1.Assignement1.Utils.Utils.passwordEncoder;


@Service
@RequiredArgsConstructor
public class PeopleServiceImplementation implements PersonService {
    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;
    private final DeviceRepository deviceRepository;
    private final SoketServiceImplementation soketServiceImplementation;

    @Override
    @Transactional
    public void update(UpdateDTO personUpdateDTOerson) {
        Person personDB = peopleRepository.findFirstById(personUpdateDTOerson.getId());

        boolean isAdmin = Boolean.parseBoolean(personUpdateDTOerson.getValue().toString());

        soketServiceImplementation.SendMessage(personDB.getId(),"S-a facut update");
        switch (personUpdateDTOerson.getField()) {
            case "isadmin":
                System.out.println(isAdmin);
                personDB.getRole().setUserType(isAdmin ? UserType.Admin : UserType.Client);
                break;
            case "username":
                personDB.getRole().setUsername(personUpdateDTOerson.getValue().toString());
                break;
            case "name":
                personDB.setName(personUpdateDTOerson.getValue().toString());
                break;
            case "address":
                personDB.setAddress(personUpdateDTOerson.getValue().toString());
                break;
            case "email":
                personDB.setEmail(personUpdateDTOerson.getValue().toString());
                try {
//                    if (personUpdateDTOerson.getValue().toString().endsWith("m")) {
//                        Utils.sendEmail(personDB.getEmail(), Utils.generatePassword());
//                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error");
                }

                break;
            case "phone":
                personDB.setPhoneNumber(personUpdateDTOerson.getValue().toString());
                break;
            case "birthdate":
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                try {
                    personDB.setBirthDate(formatter.parse(personUpdateDTOerson.getValue().toString().substring(0, 10)));
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            default:
                break;
        }
    }

    @Override
    public void delete(Person Person) {
        peopleRepository.delete(Person);
    }

    @Override
    public void add(Person Person) {
        peopleRepository.save(Person);
    }

    @Override
    public Person getByRole(Role role) {
        return peopleRepository.findFirstByRole(role);
    }

    @Override
    public List<PersonResponseDTO> getAll() {
        return peopleRepository.findAll().stream()
                .map(person -> PersonMapper.mapToPersonResponse(person, deviceRepository.findAll()))
                .collect(Collectors.toList());
    }

    @Override
    public String register(PersonRegisterDTO clientRegisterDTO) {

        String password = generatePassword(PASSWORD_LENGTH);
        Role role = Role.builder().username(clientRegisterDTO.getUsername()).userType(UserType.Client).password(passwordEncoder().encode(password)).build();
        roleRepository.save(role);
        Person person = Person.builder()
                .birthDate(clientRegisterDTO.getDate())
                .name(clientRegisterDTO.getName())
                .role(role)
                .address(clientRegisterDTO.getAddress())
                .email(clientRegisterDTO.getEmail())
                .phoneNumber(clientRegisterDTO.getPhoneNumber())
                .build();
        peopleRepository.save(person);
        return Utils.sendEmail(clientRegisterDTO.getEmail(),  clientRegisterDTO.getUsername() + " "  + password);
    }

    @Override
    @Transactional
    public boolean deleteByIds(List<Long> ids) {
        try {
            ids.stream().forEach(i -> peopleRepository.deleteById(i));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public String insert(AddPersonDTO addPersonDTO) {
        String password = generatePassword(PASSWORD_LENGTH);
        Role role = Role.builder()
                .userType(addPersonDTO.getUserType().equals("Admin") ? UserType.Admin : UserType.Client)
                .username(addPersonDTO.getUsername())
                .password(passwordEncoder().encode(password))
                .status(new ArrayList<Status>())
                .build();
        roleRepository.save(role);
        List<Device> deviceList = addPersonDTO.getDevices().stream().map(i -> deviceRepository.findFirstByName(i)).collect(Collectors.toList());

        Person person = Person.builder()
                .phoneNumber(addPersonDTO.getPhone())
                .address(addPersonDTO.getAddress())
                .name(addPersonDTO.getName())
                .email(addPersonDTO.getEmail())
                .role(role)
                .devices(deviceList)
                .birthDate(addPersonDTO.getDate())
                .build();
        peopleRepository.save(person);

        return Utils.sendEmail(addPersonDTO.getEmail(), "Your email is " + addPersonDTO.getEmail() + " and your password is " + password);
    }

    @Override
    public boolean save(MultipartFile image) {
        byte[] imageDB = null;
        try {
            imageDB = image.getBytes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean editDevices(UpdateDevicesDTO updateDevicesDTO) {
        System.out.println(updateDevicesDTO);
        Person personDB = peopleRepository.findFirstById(updateDevicesDTO.getId());
        if (personDB == null)
            return false;

        List<Device> deviceList = updateDevicesDTO.getDevices().stream().map(i -> deviceRepository.findFirstByName(i)).collect(Collectors.toList());
        if (deviceList == null)
            return false;
        personDB.setDevices(deviceList);
        return true;
    }

    @Override
    @Transactional
    public Person uploadImage(MultipartFile multipartFile,Long clientId) {
        byte []imageProfile = null;

        try {
            imageProfile = multipartFile.getBytes();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        Person person = peopleRepository.findFirstById(clientId);
        if (person == null)
            return null;
        person.setProfilePicture(imageProfile);
        return person;
    }

    @Override
    public Person getById(Long id) {
        System.out.println(id);
        System.out.println(peopleRepository.findFirstById(id));
        return peopleRepository.findFirstById(id);
    }

    @Override
    @Transactional
    public String updateProfile(UpdateProfileDTO updateProfileDTO) {
        System.out.println(updateProfileDTO);
        Person person = peopleRepository.findFirstById(updateProfileDTO.getId());
        if (person==null)
            return "NU";

        person.setName(updateProfileDTO.getName());
        person.setAddress(updateProfileDTO.getAddress());
        person.setEmail(updateProfileDTO.getEmail());
        person.getRole().setUsername(updateProfileDTO.getUsername());
        person.getRole().setPassword(updateProfileDTO.getPassword());
        person.setPhoneNumber(updateProfileDTO.getPhone());
        person.setBirthDate(updateProfileDTO.getBirthDate());
        return "DA";
    }
}