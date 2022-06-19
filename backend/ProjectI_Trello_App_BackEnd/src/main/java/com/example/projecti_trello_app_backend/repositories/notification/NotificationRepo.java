package com.example.projecti_trello_app_backend.repositories.notification;

import com.example.projecti_trello_app_backend.entities.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    List<Notification> findAll();

    Optional<Notification> findByNotificationId(int notificationId);

}
