package com.example.projecti_trello_app_backend.security.entry_point;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Rest Unauthorized",authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED");
        response.getWriter().write("Rest Unauthorized");
    }
}
