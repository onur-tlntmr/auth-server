package com.example.springjwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JwtTokenUtil {

    public static final String KEY = "secret";

    public static final String ISSUER = "example.com";

    public static final int EXPIRES = 10 * 60 * 10000;

    private final Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes());

    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String createToken(User user) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() * EXPIRES))
                .withIssuer(ISSUER)
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
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


}
