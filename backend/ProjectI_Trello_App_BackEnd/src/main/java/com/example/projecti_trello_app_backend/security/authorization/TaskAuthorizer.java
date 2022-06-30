package com.example.projecti_trello_app_backend.security.authorization;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TaskAuthorizer {

    @Around(value = "@annotation(com.example.projecti_trello_app_backend.security.authorization.TaskMember)")
    public Object authorize(ProceedingJoinPoint joinPoint)
    {
        return "";
    }
}
