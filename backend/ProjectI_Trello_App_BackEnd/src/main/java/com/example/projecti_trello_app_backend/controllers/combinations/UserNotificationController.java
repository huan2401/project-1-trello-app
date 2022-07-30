package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.combinations.UserNotificationService;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.notification.NotificationService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "User Notification Controller",
description = "Use to represent the relationship between users and notifications")
@RestController
@RequestMapping("project1/api/user-notification")
public class UserNotificationController {

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private TaskService taskService ;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Find all user_noti_s by id")
    @GetMapping(path = "/find-by-id")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findById(@RequestParam(name = "id") int id,
                                      HttpServletRequest request){
        Optional<UserNotification> usNotiOptional = userNotificationService.findById(id);
        return usNotiOptional.isPresent()?ResponseEntity.ok(usNotiOptional)
                                         : ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find all user_noti_s of a user")
    @GetMapping(path = "/find-by-user")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id")int userId,
                                        HttpServletRequest request)
    {
        return userNotificationService.findByUser(userId).isEmpty()
                ? ResponseEntity.ok(userNotificationService.findByUser(userId))
                :ResponseEntity.noContent().build();
    }

    // send notifications about a task update to all member of the task (for updates in a task)
    @Operation(summary = "Send notifications about task update to all member of the task (for updates in a task)")
    @GetMapping(path = "/send-update-noti")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> sendUpdateNotifications(@RequestParam(name = "task_id")int taskId,
                                                     @RequestBody Notification notification,
                                                     HttpServletRequest request)
    {
        User byUser = util.getUserFromRequest(request);
        if(!taskService.findByTaskId(taskId).isPresent())
            return ResponseEntity.noContent().build();
        UserNotification userNotification = UserNotification.builder().build();
        Optional<Notification> notificationOptional = notificationService.add(notification);
        if(!notificationOptional.isPresent()) return ResponseEntity.noContent().build();
        userNotification.setNotification(notificationOptional.get());
        List<User> userList = userTaskService.findByTask(taskId).stream().map(userTask -> userTask.getUser()).collect(Collectors.toList());
        for( User user : userList)
        {
            userNotification.setUser(user);
            if(!userNotificationService.sendNotification(userNotification).isPresent())
                return ResponseEntity.noContent().build();
            userNotificationService.sendNotificationEmail(byUser,user,taskService.findByTaskId(taskId).get(),"update");
        }
        return ResponseEntity.status(200).build();
    }

    // send notification to a user who has been added to a board
    @Operation(summary = "Send notifications to a user who has been added to a board")
    @GetMapping(path = "/send-add-to-board-noti")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> sendAddToBoardNotifications(@RequestParam(name = "board_id", required = false) int boardId,
                                                         @RequestParam(name = "user_id") int userId,
                                                         HttpServletRequest request)
    {
        User byUser = util.getUserFromRequest(request);
        Notification notification = new Notification();
        UserNotification userNotification = UserNotification.builder().build();
        return boardService.findByBoardId(boardId).map(board -> {
            return userService.findByUserId(userId).map(user -> {
                notification.setNotificationContent("You were added to "+board.getBoardTitle());
                Optional<Notification> notiOptional = notificationService.add(notification);
                if(!notiOptional.isPresent()) return ResponseEntity.status(304).build();
                userNotification.setNotification(notiOptional.get());
                userNotification.setUser(user);
                return userNotificationService.sendNotification(userNotification).isPresent()
                        && userNotificationService.sendNotificationEmail(byUser,user,board,"add")
                        ?ResponseEntity.status(200).body(new MessageResponse("Send email successfully"))
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    //send notification to a user who has been add to a task
    @Operation(summary = "Send notification to a user who has been added to a task")
    @PostMapping(path = "/send-add-to-task-noti")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> sendAddToTaskNotification(@RequestParam(name = "task_id")int taskId,
                                                       @RequestParam(name = "user_id")int userId,
                                                       HttpServletRequest request)
    {
        User byUser = util.getUserFromRequest(request);
        Notification notification = Notification.builder().build();
        UserNotification userNotification = UserNotification.builder().build();
        return taskService.findByTaskId(taskId).map(task -> {
            return userService.findByUserId(userId).map(user -> {
                notification.setNotificationContent("You were added to "+task.getTaskName());
                Optional<Notification> notiOptional = notificationService.add(notification);
                if(!notiOptional.isPresent()) return ResponseEntity.status(304).build();
                userNotification.setNotification(notiOptional.get());
                userNotification.setUser(user);
                return userNotificationService.sendNotification(userNotification).isPresent()
                        && userNotificationService.sendNotificationEmail(byUser,user,task,"add")
                        ?ResponseEntity.status(200).body(new MessageResponse("Send email successfully"))
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    @Operation(summary = "Send a notification to a user who has been removed from a board")
    @GetMapping(path = "send-remove-from-board-noti")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> sendRemoveFromBoardNoti(@RequestParam(name = "board_id", required = false) int boardId,
                                                     @RequestParam(name = "user_id") int userId,
                                                     HttpServletRequest request)
    {
        User byUser = util.getUserFromRequest(request);
        Notification notification = Notification.builder().build();
        UserNotification userNotification = UserNotification.builder().build();
        return boardService.findByBoardId(boardId).map(board -> {
            return userService.findByUserId(userId).map(user -> {
                notification.setNotificationContent("You were removed from "+board.getBoardTitle());
                Optional<Notification> notiOptional = notificationService.add(notification);
                if(!notiOptional.isPresent()) return ResponseEntity.status(304).build();
                userNotification.setNotification(notiOptional.get());
                userNotification.setUser(user);
                return userNotificationService.sendNotification(userNotification).isPresent()
                        && userNotificationService.sendNotificationEmail(byUser,user,board,"remove")
                        ?ResponseEntity.status(200).body("Send email successfully")
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    @Operation(summary = "Send a notification to a user who has been removed from a task" +
            " an email will be sent to the user")
    @GetMapping(path = "/send-remove-from-task-noti")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> sendRemoveFromTaskNoti(@RequestParam(name = "task_id")int taskId,
                                                    @RequestParam(name = "user_id")int userId,
                                                    HttpServletRequest request)
    {
        User byUser = util.getUserFromRequest(request);
        Notification notification = Notification.builder().build();
        UserNotification userNotification = UserNotification.builder().build();
        return taskService.findByTaskId(taskId).map(task -> {
            return userService.findByUserId(userId).map(user -> {
                notification.setNotificationContent("You were removed from "+task.getTaskName());
                Optional<Notification> notiOptional = notificationService.add(notification);
                if(!notiOptional.isPresent()) return ResponseEntity.status(304).build();
                userNotification.setNotification(notiOptional.get());
                userNotification.setUser(user);
                return userNotificationService.sendNotification(userNotification).isPresent()
                        && userNotificationService.sendNotificationEmail(byUser,user,task,"remove")
                        ?ResponseEntity.status(200).body(new MessageResponse("Send email successfully"))
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }
}
