package com.example.projecti_trello_app_backend.security.authorization.authorizer;

import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class TaskAuthorizer {

    @Autowired
    SecurityUtils util;

    @Around(value = "@annotation(com.example.projecti_trello_app_backend.security.authorization.RequireTaskMember)")
    public Object authorize(ProceedingJoinPoint joinPoint)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        List<String> listParams = Arrays.asList(signature.getParameterNames());
        int requestIndex = listParams.indexOf("request");
        int taskIndex = listParams.indexOf("taskId");
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[requestIndex];
        Integer taskId = (Integer) joinPoint.getArgs()[taskIndex];
        return "";
    }

}
