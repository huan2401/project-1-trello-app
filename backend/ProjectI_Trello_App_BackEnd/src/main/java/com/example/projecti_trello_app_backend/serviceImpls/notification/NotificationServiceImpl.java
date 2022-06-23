package com.example.projecti_trello_app_backend.serviceImpls.notification;

import com.example.projecti_trello_app_backend.entities.notification.Notification;
import com.example.projecti_trello_app_backend.repositories.notification.NotificationRepo;
import com.example.projecti_trello_app_backend.services.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public List<Notification> findAll() {
        try{
            return notificationRepo.findAll();
        } catch (Exception ex)
        {
            log.error("find all notification error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Notification> findByNotificationId(int notificationId) {
        try{
            return notificationRepo.findByNotificationId(notificationId);
        } catch (Exception ex)
        {
            log.error("find notification by id error", ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Notification> add(Notification notification) {
        try{
            notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(notificationRepo.save(notification));
        } catch (Exception ex)
        {
            log.error("add notification error",ex);
            return Optional.empty();
        }
    }
}
