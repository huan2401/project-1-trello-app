package com.example.projecti_trello_app_backend.repositories.user;

import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

     List<User> findAll();

     Optional<User> findByUserId(int userId);

     Optional<User> findByUserNameOrEmail(String userName, String email);
}
