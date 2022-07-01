package com.example.projecti_trello_app_backend.security.authorization.authorizer;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.awt.desktop.OpenFilesEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class WorkspaceAuthorizer {

    @Autowired
   SecurityUtils util;

    @Around("@annotation(com.example.projecti_trello_app_backend.security.authorization.RequireWorkspaceCreator)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        List<String> listParams = Arrays.asList(signature.getParameterNames());
        int workSpaceIdIndex = listParams.indexOf("workSpaceId");
        int requestIndex = listParams.indexOf("request");
        HttpServletRequest request =(HttpServletRequest) joinPoint.getArgs()[requestIndex];
        Integer workspaceId = (Integer) joinPoint.getArgs()[workSpaceIdIndex];
        if(!util.getUserWorkspaceRole(request,workspaceId.intValue()).equals("WS_CREATOR"))
            throw  new AccessDeniedException("Access Denied");
        Object result = joinPoint.proceed();
        return result;
    }
}
