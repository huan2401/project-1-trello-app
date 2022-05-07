package com.example.projecti_trello_app_backend.services.user;

import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAll();

    Optional<User> findByUserId(int userId);

    Optional<User> findByUsernameOrEmail(String userName, String email);

    Optional<User> signUp(User user); // add new user

    Optional<?> update(UserDTO userDTO);

    Optional<User> changePassWord(User user);

}
