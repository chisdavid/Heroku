package com.ds.Assignement1.Assignement1.Service.Implementation;

import com.ds.Assignement1.Assignement1.Dto.AuthenticationRequest;
import com.ds.Assignement1.Assignement1.Dto.AuthenticationResponse;
import com.ds.Assignement1.Assignement1.Mapper.PersonMapper;
import com.ds.Assignement1.Assignement1.Model.*;
import com.ds.Assignement1.Assignement1.Repository.DeviceRepository;
import com.ds.Assignement1.Assignement1.Repository.PeopleRepository;
import com.ds.Assignement1.Assignement1.Repository.RoleRepository;
import com.ds.Assignement1.Assignement1.Service.RoleService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.ds.Assignement1.Assignement1.Utils.Utils.passwordEncoder;


@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {
    private final RoleRepository roleRepository;
    private final PeopleRepository peopleRepository;
    private final DeviceRepository deviceRepository;
//    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void update(Role role) {
        Role roleDB = roleRepository.findFirstById(role.getId()).get();
        roleDB.setPassword(role.getPassword());
        roleDB.setUsername(role.getUsername());
        roleDB.setUserType(role.getUserType());
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public Person logIn(AuthenticationRequest authenticationRequest) {
        Role role = roleRepository.findFirstByUsername(authenticationRequest.getUsername()).get();

        List<Status> status = role.getStatus();
        status.add(Status.builder().logIn(LocalDateTime.now()).build());
        role.setStatus(status);

        if (role != null) {
            if ( passwordEncoder().matches(authenticationRequest.getPassword(),role.getPassword())) {
                Person person = peopleRepository.findFirstByRole(role);
                System.out.println(person);
                return person;
              }
        }
        return null;
    }

    @Override
    public Role getByUsername(String username) {
        return roleRepository.findFirstByUsername(username).get();
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public boolean logOut(Long id) {
        if (id == null)
            return false;
        Person personDB = peopleRepository.findFirstById(id);
        if (personDB == null)
            return false;
        personDB.getRole().getStatus().get(personDB.getRole().getStatus().size() - 1).setLogOut(LocalDateTime.now());
        return true;
    }
}