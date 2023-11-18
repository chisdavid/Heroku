package com.ds.Assignement1.Assignement1.Repository;

import com.ds.Assignement1.Assignement1.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Optional<Role> findFirstById(Long id);
    Optional<Role> findFirstByUsername(String username);
}