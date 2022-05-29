package com.example.projecti_trello_app_backend.repositories.user;

import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

     @Query(value = "from User user where user.activated =true")
     List<User> findAll();

     @Query(value = "from User user where user.userId=?1 and user.activated = true")
     Optional<User> findByUserId(int userId);

     @Query(value = "from User user where (user.userName=?1 or user.email=?2) and user.activated=true")
     Optional<User> findByUserNameOrEmail(String userName, String email);

     Optional<User> findUserByVerificationCode(String verificationCode);
}
