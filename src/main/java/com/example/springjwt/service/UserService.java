package com.example.springjwt.service;

import com.example.springjwt.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUser(String username);

    List<User> getUsers();

}
