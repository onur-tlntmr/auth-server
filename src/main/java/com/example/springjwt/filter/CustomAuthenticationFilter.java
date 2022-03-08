package com.example.springjwt.filter;

import com.example.springjwt.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {

        User user = (User) authResult.getPrincipal();


        String access_token = jwtUtil.createToken(user);


        String refresh_token = jwtUtil.createToken(user);


        Map<String, String> tokens = Map.of("access_token", access_token,
                "refresh_token", refresh_token);

        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

    }


}
