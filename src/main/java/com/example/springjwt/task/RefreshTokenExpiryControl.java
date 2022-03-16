package com.example.springjwt.task;

import com.example.springjwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenExpiryControl {

    private final TokenService tokenService;

    /**
     * Every new day cleanup expired tokens
     **/
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredRefreshTokens() {
        tokenService.cleanExpiredTokens();
    }


}
