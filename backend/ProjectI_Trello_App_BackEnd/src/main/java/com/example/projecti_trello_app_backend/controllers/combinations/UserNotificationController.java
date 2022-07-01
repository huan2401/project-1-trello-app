package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.combinations.UserNotificationService;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.notification.NotificationService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("project1/api/user-notification")
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping(path = "/find-by-id")
    public ResponseEntity<?> findById(@RequestParam(name = "id") int id,
                                      HttpServletRequest request){
        Optional<UserNotification> usNotiOptional = userNotificationService.findById(id);
        return usNotiOptional.isPresent()?ResponseEntity.ok(usNotiOptional)
                                         : ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/find-by-user")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id")int userId,
                                        HttpServletRequest request)
    {
        return userNotificationService.findByUser(userId).isEmpty()
                ? ResponseEntity.ok(userNotificationService.findByUser(userId))
                :ResponseEntity.noContent().build();
    }

    // send notifications about a task update to all member of the task (for updates in a task)
    @GetMapping(path = "/send-update-noti")
    public ResponseEntity<?> sendUpdateNotifications(@RequestParam(name = "task_id")int taskId,
                                                     @RequestBody Notification notification,
                                                     HttpServletRequest request)
    {
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
        }
        return ResponseEntity.status(200).build();
    }

    // send notification to a user who has been added to a board
    @GetMapping(path = "/send-add-to-board-noti")
    public ResponseEntity<?> sendAddToBoardNotifications(@RequestParam(name = "board_id", required = false) int boardId,
                                                         @RequestParam(name = "user_id") int userId,
                                                         HttpServletRequest request)
    {
        Notification notification = Notification.builder().build();
        UserNotification userNotification = UserNotification.builder().build();
        return boardService.findByBoardId(boardId).map(board -> {
            return userService.findByUserId(userId).map(user -> {
                notification.setNotificationContent("You were added to "+board.getBoardTitle());
                Optional<Notification> notiOptional = notificationService.add(notification);
                if(!notiOptional.isPresent()) return ResponseEntity.status(304).build();
                userNotification.setNotification(notiOptional.get());
                userNotification.setUser(user);
                return userNotificationService.sendNotification(userNotification).isPresent()
                        ?ResponseEntity.status(200).build()
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    //send notification to a user who has been add to a task
    @PostMapping(path = "/send-add-to-task-noti")
    public ResponseEntity<?> sendAddToTaskNotification(@RequestParam(name = "task_id")int taskId,
                                                       @RequestParam(name = "user_id")int userId,
                                                       HttpServletRequest request)
    {
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
                        ?ResponseEntity.status(200).build()
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    @GetMapping(path = "send-remove-from-board-noti")
    public ResponseEntity<?> sendRemoveFromBoardNoti(@RequestParam(name = "board_id", required = false) int boardId,
                                                     @RequestParam(name = "user_id") int userId,
                                                     HttpServletRequest request)
    {
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
                        ?ResponseEntity.status(200).build()
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    @GetMapping(path = "/send-remove-from-task-noti")
    public ResponseEntity<?> sendRemoveFromTaskNoti(@RequestParam(name = "task_id")int taskId,
                                                    @RequestParam(name = "user_id")int userId,
                                                    HttpServletRequest request)
    {
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
                        ?ResponseEntity.status(200).build()
                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }
}
