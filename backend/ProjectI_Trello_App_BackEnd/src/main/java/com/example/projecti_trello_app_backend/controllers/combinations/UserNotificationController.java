package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.combinations.UserNotificationService;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("project1/api/user-notification")
public class UserNotificationController {

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserTaskService userTaskService;

    @GetMapping(path = "/find-by-id")
    public ResponseEntity<?> findById(@RequestParam(name = "id") int id){
        Optional<UserNotification> usNotiOptional = userNotificationService.findById(id);
        return usNotiOptional.isPresent()?ResponseEntity.ok(usNotiOptional)
                                         : ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/find-by-user")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id")int userId)
    {
        return userNotificationService.findByUser(userId).isEmpty()
                ? ResponseEntity.ok(userNotificationService.findByUser(userId))
                :ResponseEntity.noContent().build();
    }


    // send notifications about a task update to all member of the task (for updates in a task)
    @PostMapping(path = "/send-update-noti")
    public ResponseEntity<?> sendUpdateNotifications(@RequestParam(name = "task_id")int taskId,
                                                     @RequestBody Notification notification)
    {
        UserNotification userNotification = UserNotification.builder().build();
        Optional<Notification> notificationOptional = notificationService.add(notification);
        if(!notificationOptional.isPresent()) return ResponseEntity.noContent().build();
        userNotification.setNotification(notificationOptional.get());
        List<User> userList = userTaskService.findByTask(taskId).stream().map(userTask -> userTask.getUser()).collect(Collectors.toList());
        for( var user : userList)
        {
            userNotification.setUser(user);
            if(!userNotificationService.sendUpdateNotification(userNotification).isPresent())
                return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(200).build();
    }

    // send notification to a user who has been added to a board
    @PostMapping(path = "/send-add-to-board-noti")
    public ResponseEntity<?> sendAddToBoardNotifications(@RequestParam(name = "board_id", required = false) int boardId,
                                                  @RequestParam(name = "user_id") int userId)
    {
        return ResponseEntity.ok("");
    }

    //send notification to a user who has been add to a task
    @PostMapping(path = "/send-add-to-task-noti")
    public ResponseEntity<?> sendAddToTaskNotification(@RequestParam(name = "task_id")int taskId,
                                                       @RequestParam(name = "user_id")int userId)
    {
        return ResponseEntity.ok("");
    }
}
