package com.example.projecti_trello_app_backend.services.notification;

import com.example.projecti_trello_app_backend.entities.notification.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NotificationService {

    List<Notification> findAll();

    Optional<Notification> findByNotificationId(int notificationId);

    Optional<Notification> add(Notification notification);
}
