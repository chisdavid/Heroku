package com.ds.Assignement1.Assignement1.Service;


import com.ds.Assignement1.Assignement1.Dto.AuthenticationRequest;
import com.ds.Assignement1.Assignement1.Dto.AuthenticationResponse;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Model.Role;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface RoleService {
    void update(Role role);
    void delete(Role role);
    void add(Role role);
    Person logIn(AuthenticationRequest authenticationRequest);
    Role getByUsername(String username);
    List<Role> getAll();
    boolean logOut(Long id);
}