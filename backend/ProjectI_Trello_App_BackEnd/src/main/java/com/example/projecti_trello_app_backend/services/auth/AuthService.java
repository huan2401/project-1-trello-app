package com.example.projecti_trello_app_backend.services.auth;

import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AuthService {

    public Optional<User> signUp(User user, String siteURL);

    public void sendVerificationEmail(User user, String siteURL);

    public Optional<User> verifyUser(String verificationCode);
}
