package com.example.springjwt.repository;

import com.example.springjwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByToken(String token);

    boolean existsRefreshTokenByToken(String token);

    void deleteAllByExpiryDateBefore(LocalDate expiryDate);


}
