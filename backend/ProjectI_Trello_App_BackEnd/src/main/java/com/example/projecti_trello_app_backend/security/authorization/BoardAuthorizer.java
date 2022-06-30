package com.example.projecti_trello_app_backend.security.authorization;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BoardAuthorizer {

    @Around("@annotation(com.example.projecti_trello_app_backend.security.authorization.BoardAdmin)")
    public Object authorizer(ProceedingJoinPoint joinPoint)
    {
        return "";
    }
}
