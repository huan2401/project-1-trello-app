package com.example.projecti_trello_app_backend.security.authorization.authorizer;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
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

    @Autowired
    TaskService taskService;

    @Autowired
    ColumnTaskService columnTaskService;


    @Around("@annotation(com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin)")
    public Object authorizer(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        List<String> listParams = Arrays.stream(signature.getParameterNames()).collect(Collectors.toList());
        int requestIndex =listParams.indexOf("request");
        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[requestIndex];
        int boardId = 1;
        if(listParams.contains("taskId"))
        {
            int taskIndex = listParams.indexOf("taskId");
            Integer taskId = (Integer) joinPoint.getArgs()[taskIndex];
            if(!taskService.findByTaskId(taskId.intValue()).isPresent())
                return  null;
            boardId = columnTaskService.findAllByTask(taskId.intValue()).get(0).getColumn().getBoard().getBoardId();
        } else if(listParams.contains("columnId"))
        {
            int columnIndex = listParams.indexOf("columnId");
            Integer columnId = (Integer) joinPoint.getArgs()[columnIndex];
            if(!columnService.findByColumnId(columnId).isPresent()) return null;
            boardId = columnService.findByColumnId(columnId.intValue()).get().getBoard().getBoardId();
        }
        else{
            int boardIndex = listParams.indexOf("boardId");
            boardId = ((Integer) joinPoint.getArgs()[boardIndex]).intValue();
        }
        if(!util.getUserBoardRole(request,boardId).equals("BOARD_ADMIN")){
          //  return ResponseEntity.status(403).body(new MessageResponse("Access Denied"));
            throw new AccessDeniedException("Access Denied");
        }
        return joinPoint.proceed(); // the main method is handled
    }
}
