package com.ds.Assignement1.Assignement1.Controller;


import com.ds.Assignement1.Assignement1.Dto.AuthenticationRequest;
import com.ds.Assignement1.Assignement1.Mapper.PersonMapper;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Security.JwtTokenUtil;
import com.ds.Assignement1.Assignement1.Service.Implementation.DeviceServiceImplementation;
import com.ds.Assignement1.Assignement1.Service.Implementation.RoleServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.ds.Assignement1.Assignement1.Utils.Utils.genericResponse;

@CrossOrigin
@Controller
@RequestMapping("/Role")

public class RoleController {
    @Autowired
    private RoleServiceImplementation roleServiceImplementation;
    @Autowired
    private  JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DeviceServiceImplementation deviceServiceImplementation;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/GetAll")
    public ResponseEntity getAll() {
        return genericResponse(roleServiceImplementation.getAll());
    }

    @PostMapping("/GetByUserName")
    public ResponseEntity getByUsername(@RequestBody String username) {
        return genericResponse(roleServiceImplementation.getByUsername(username));
    }

    @PostMapping("/LogIn")
    public ResponseEntity LogIn(@RequestBody AuthenticationRequest authenticationRequest) {

        try{
            Person person = roleServiceImplementation.logIn(authenticationRequest);
            if (person != null)
            {
                return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwtTokenUtil.generateToken(person.getRole())).body(PersonMapper.mapToPersonResponse( person,deviceServiceImplementation.getAll()));
            }
            else return ResponseEntity.notFound().build();
        }
        catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/LogOut")
    public ResponseEntity LogOut(@RequestBody Long id) {
        return genericResponse(roleServiceImplementation.logOut(id));
    }
}