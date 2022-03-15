package com.example.springjwt.service;

import com.example.springjwt.entity.User;

import java.util.Map;


public interface TokenService {


    Map<String, String> createTokens(String userName);

    Map<String, String> createTokens(User user);

    Map<String, String> refreshTokens(String refreshToken);


    void cleanExpiredTokens();

}
