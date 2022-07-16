package com.example.projecti_trello_app_backend.services.token;

import com.example.projecti_trello_app_backend.entities.token.RefreshToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RefreshTokenService {

    Optional<RefreshToken> findById(int id);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> save(RefreshToken refreshToken);

    String generateRefreshToken(User user);

    boolean verifyRefreshToken(String token);

    boolean setExpiredByToken(String token);
}
