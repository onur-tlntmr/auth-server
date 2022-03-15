package com.example.springjwt.service;

import com.example.springjwt.entity.RefreshToken;
import com.example.springjwt.entity.User;
import com.example.springjwt.repository.RefreshTokenRepo;
import com.example.springjwt.repository.UserRepo;
import com.example.springjwt.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepo tokenRepo;
    private final UserRepo userRepo;
    private final JwtTokenUtil jwtUtil;


    private RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = new RefreshToken();

        // Generate refresh token
        String token;

        // Until the refresh token is unique
        do {

            token = jwtUtil.generateRefreshToken();

        } while (tokenRepo.existsRefreshTokenByToken(token));


        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(LocalDate.now().plusMonths(6));

        tokenRepo.save(refreshToken);

        return refreshToken;
    }

    @Override
    public Map<String, String> createTokens(String userName) {

        User user = userRepo.findByName(userName);

        String access_token = jwtUtil.createJwtToken(user);

        RefreshToken refreshToken = createRefreshToken(user);

        return Map.of("access_token", access_token,
                "refresh_token", refreshToken.getToken());

    }

    @Override
    public Map<String, String> createTokens(User user) {


        String access_token = jwtUtil.createJwtToken(user);

        RefreshToken refreshToken = createRefreshToken(user);

        return Map.of("access_token", access_token,
                "refresh_token", refreshToken.getToken());

    }

    @Override
    public Map<String, String> refreshTokens(String refreshToken) {

        RefreshToken token = tokenRepo.findByToken(refreshToken);

        if (token != null) {

            tokenRepo.delete(token);

            return createTokens(token.getUser());
        } else
            throw new RuntimeException("Invalid refresh token");

    }


    @Override
    public void cleanExpiredTokens() {

        LocalDate now = LocalDate.now();

        tokenRepo.deleteAllByExpiryDateBefore(now);
    }
}
