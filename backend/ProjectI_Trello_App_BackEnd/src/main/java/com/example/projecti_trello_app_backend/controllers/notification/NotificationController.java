package com.example.projecti_trello_app_backend.controllers.notification;

import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.OpenFilesEvent;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(path = "/find-all")
    public ResponseEntity<?> findAll(){
        return notificationService.findAll().isEmpty()?ResponseEntity.noContent().build()
                                                      :ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping(path = "/find-by-id")
    public ResponseEntity<?> findByNotificationId(@RequestParam(name = "noti_id") int notificationId)
    {
        return notificationService.findByNotificationId(notificationId).isPresent()
                ?ResponseEntity.ok(notificationService.findByNotificationId(notificationId))
                :ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestBody Notification notification)
    {
        Optional<Notification> notificationOptional = notificationService.add(notification);
        return notificationOptional.isPresent()?ResponseEntity.ok(notificationOptional)
                                                :ResponseEntity.noContent().build();
    }
}
