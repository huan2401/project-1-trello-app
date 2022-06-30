package com.example.projecti_trello_app_backend.security.authorization;

import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.desktop.OpenFilesEvent;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class WorkspaceAuthorizer {

    @Autowired
   SecurityUtils util;

    @Around("@annotation(WorkspaceCreator)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable
    {
        int length = joinPoint.getArgs().length;
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[length-1];
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object result = joinPoint.proceed();
        return result;
    }
}
