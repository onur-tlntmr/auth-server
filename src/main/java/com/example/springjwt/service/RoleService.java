package com.example.springjwt.service;

import com.example.springjwt.entity.Role;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RoleService extends Repository<Role, Short> {

    List<Role> getRoles();

}
