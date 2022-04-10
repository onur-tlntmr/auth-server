package com.example.springjwt.service;

import com.example.springjwt.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Map;


public interface TokenService {


    Map<String, String> createTokens(String userName);

    Map<String, String> createTokens(User user);

    UsernamePasswordAuthenticationToken getAuthToken(String token);

    Map<String, String> refreshTokens(String refreshToken);

    void deleteRefreshToken(String token);

    void cleanExpiredTokens();

}
