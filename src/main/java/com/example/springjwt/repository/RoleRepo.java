package com.example.springjwt.repository;

import com.example.springjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Short> {

    Role findByName(String name);

}
