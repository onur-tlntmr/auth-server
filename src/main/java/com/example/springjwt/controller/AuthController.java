package com.example.springjwt.controller;


import com.example.springjwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshTokens(@RequestParam String refreshToken) {

        Map<String, String> newTokens = tokenService.refreshTokens(refreshToken);

        return ResponseEntity.ok(newTokens);

    }

}

