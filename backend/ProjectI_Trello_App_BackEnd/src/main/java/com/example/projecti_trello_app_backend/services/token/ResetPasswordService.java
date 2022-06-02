package com.example.projecti_trello_app_backend.services.token;

import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Service
public interface ResetPasswordService {

    public Optional<ResetPasswordToken> findByToken(String token);

    public boolean createResetPasswordToken(User user);

    public boolean validateToken(String token);

    public boolean setExpired(String token);

    public boolean sendResetPasswordEmail(User user, ResetPasswordToken resetPasswordToken);
}
