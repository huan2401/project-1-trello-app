package com.example.projecti_trello_app_backend.serviceImpls.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import com.example.projecti_trello_app_backend.repositories.combinations.UserNotificationRepo;
import com.example.projecti_trello_app_backend.services.combinations.UserNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepo userNotificationRepo;

    @Override
    public Optional<UserNotification> findById(int id) {
        try{
            return userNotificationRepo.findById(id);
        }catch (Exception ex)
        {
            log.error("find User Notification error", ex);
            return Optional.empty();
        }
    }

    @Override
    public List<UserNotification> findByUser(int userId) {
        try {
            return userNotificationRepo.findByUser(userId);
        } catch (Exception ex)
        {
            log.error("find UserNotification by User",ex);
            return Collections.emptyList();
        }
    }

    // add new UserNotification to table
    @Override
    public Optional<UserNotification> sendNotification(UserNotification userNotification) {
        try{
            userNotification.setSentAt(new Timestamp(System.currentTimeMillis()));
            userNotification.setRead(false);
            return Optional.ofNullable(userNotificationRepo.save(userNotification));
        } catch (Exception ex)
        {
            log.error("send notification error",ex);
            return Optional.empty();
        }
    }
}
