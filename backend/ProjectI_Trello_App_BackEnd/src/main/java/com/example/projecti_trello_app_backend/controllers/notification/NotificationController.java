package com.example.projecti_trello_app_backend.controllers.notification;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.services.notification.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.desktop.OpenFilesEvent;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/notification")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll(){
        return notificationService.findAll().isEmpty()
                ?ResponseEntity.noContent().build()
                :ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping(path = "/find-by-id")
    public ResponseEntity<?> findByNotificationId(@RequestParam(name = "noti_id") int notificationId,
                                                  HttpServletRequest request)
    {
        return notificationService.findByNotificationId(notificationId).isPresent()
                ?ResponseEntity.ok(notificationService.findByNotificationId(notificationId))
                :ResponseEntity.status(204).body(new MessageResponse("Not found notification"));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestBody Notification notification,
                                 HttpServletRequest request)
    {
        Optional<Notification> notificationOptional = notificationService.add(notification);
        return notificationOptional.isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Add notification successfully"))
                :ResponseEntity.status(304).body(new MessageResponse("Add notificaion fail"));
    }
}
