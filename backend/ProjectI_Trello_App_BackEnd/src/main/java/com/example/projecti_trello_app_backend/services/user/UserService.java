package com.example.projecti_trello_app_backend.services.user;

import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAll();

    Optional<User> findByUserId(int userId);

    Optional<User> findByUsernameOrEmail(String userName, String email);

    Optional<User> signUp(User user, String siteURL); // add new user

    Boolean existsByUsernameOrEmail(String userName, String email);

    void sendVerificationEmail(User user, String siteURL);

    Optional<User> verifyUser(String verificationCode);

    Optional<?> update(UserDTO userDTO);

    Optional<User> changePassWord(User user);

    Optional<User> resetPassword(UserDTO userDTO,String token);

}
