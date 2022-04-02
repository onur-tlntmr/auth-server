package com.example.springjwt.exception;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiException exception =
                new ApiException("Invalid username or password!", status);

        JSONObject jsonException = new JSONObject(exception);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        out.print(jsonException);

        out.flush();

    }
}
