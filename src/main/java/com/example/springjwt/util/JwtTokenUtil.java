package com.example.springjwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springjwt.entity.Role;
import com.example.springjwt.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JwtTokenUtil {

    public static final String KEY = "secret";

    public static final String ISSUER = "example.com";

    public static final int EXPIRES = 10 * 10 * 60 * 10000;

    private final Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes());

    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String createJwtToken(User user) {

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(user.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() * EXPIRES))
                .withIssuer(ISSUER)
                .withClaim("roles", roles)
                .sign(algorithm);
    }


    public UsernamePasswordAuthenticationToken getAuthToken(String token) {

        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();

        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(username,
                null, authorities);

    }


    public String generateRefreshToken() {


        return UUID.randomUUID()
                .toString()
                .replace("-", "");

    }


}
