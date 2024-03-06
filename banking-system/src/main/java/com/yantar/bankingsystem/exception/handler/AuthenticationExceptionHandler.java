package com.yantar.bankingsystem.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public AuthenticationExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ProblemDetail body = ProblemDetail.forStatus(status.value());

        response.setStatus(status.value());

        body.setTitle(status.name());
        body.setDetail(authException.getMessage());
        body.setInstance(URI.create(request.getRequestURI()));

        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
