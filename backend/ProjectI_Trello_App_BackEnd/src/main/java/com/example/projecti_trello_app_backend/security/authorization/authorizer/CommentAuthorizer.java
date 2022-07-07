package com.example.projecti_trello_app_backend.security.authorization.authorizer;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class CommentAuthorizer {

    @Autowired
    SecurityUtils util;

    @Around(value = "@annotation(com.example.projecti_trello_app_backend.security.authorization.RequireCommentCreator)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        List<String> params  = Arrays.asList(signature.getParameterNames());
        int requestIndex = params.indexOf("request");
        int commentIndex = params.indexOf("commentId");
        HttpServletRequest request =(HttpServletRequest) joinPoint.getArgs()[requestIndex];
        Integer commentId = (Integer) joinPoint.getArgs()[commentIndex];
        if(!util.checkUserComment(request,commentId.intValue()))
        {
            throw new AccessDeniedException("Access Denied");
        }
        return joinPoint.proceed(); // the main method is handled
    }
}
