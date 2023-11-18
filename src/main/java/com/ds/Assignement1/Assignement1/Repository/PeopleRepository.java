package com.ds.Assignement1.Assignement1.Repository;


import com.ds.Assignement1.Assignement1.Model.Device;
import com.ds.Assignement1.Assignement1.Model.Person;
import com.ds.Assignement1.Assignement1.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends CrudRepository<Person, Long> {
    Person findFirstById(Long id);
    Person findFirstByRole(Role role);
    List<Person> findAll();
    Person findFirstByDevicesContains(Device device);
}