package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationRepo extends JpaRepository<UserNotification,Integer> {

    @Query(value = "select userNoti from UserNotification userNoti where userNoti.user.userId=?1" +
            " order by userNoti.sentAt desc")
    List<UserNotification> findByUser(int userId);

    @Query(value = "select userNoti from UserNotification userNoti where userNoti.id =?1 ")
    Optional<UserNotification> findById(int id);

    @Query(value = " select count (usNoti) from UserNotification usNoti where usNoti.isRead=false")
    int countUnreadNotification(int userId);

    @Transactional
    @Modifying
    @Query(value = "update UserNotification userNoti set userNoti.isRead =true where userNoti.id =?1")
    int setRead(int id);
}
