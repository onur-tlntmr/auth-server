package com.example.springjwt.service;

import com.example.springjwt.entity.Role;
import com.example.springjwt.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

}
