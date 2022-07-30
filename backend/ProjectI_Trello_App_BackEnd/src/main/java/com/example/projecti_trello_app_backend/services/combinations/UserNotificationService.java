package com.example.projecti_trello_app_backend.services.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserNotificationService {

    Optional<UserNotification> findById(int id);

    List<UserNotification> findByUser(int userId);

    Optional<UserNotification> sendNotification(UserNotification userNotification);

    boolean sendNotificationEmail(User byUser, User targetUser, Object obj,String emailType);



}
