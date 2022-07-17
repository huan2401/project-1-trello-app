package com.example.projecti_trello_app_backend.security.authorization.authorizer;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class BoardAuthorizer {

    @Autowired
    SecurityUtils util;

    @Autowired
    ColumnService columnService;


    @Around("@annotation(com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin)")
    public Object authorizer(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        List<String> listParams = Arrays.stream(signature.getParameterNames()).collect(Collectors.toList());
        int columnIdIndex = listParams.indexOf("columnId");
        int requestIndex =listParams.indexOf("request");
        HttpServletRequest request =(HttpServletRequest) joinPoint.getArgs()[requestIndex];
        Integer columnId  = (Integer) joinPoint.getArgs()[columnIdIndex];
        if(!columnService.findByColumnId(columnId.intValue()).isPresent()) return null;
        int boardId = columnService.findByColumnId(columnId.intValue()).get().getBoard().getBoardId();
        if(!util.getUserBoardRole(request,boardId).equals("BOARD_ADMIN")){
          //  return ResponseEntity.status(403).body(new MessageResponse("Access Denied"));
            throw new AccessDeniedException("Access Denied");
        }
        return joinPoint.proceed(); // the main method is handled
    }
}
