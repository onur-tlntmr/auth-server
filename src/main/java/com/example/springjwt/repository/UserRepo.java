package com.example.springjwt.repository;

import com.example.springjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Long> {

    User findByUserName(String name);

    User findByEmail(String email);

}
