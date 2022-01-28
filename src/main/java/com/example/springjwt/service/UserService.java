package com.example.springjwt.service;

import com.example.springjwt.model.Role;
import com.example.springjwt.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

}
