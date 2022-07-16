package com.example.projecti_trello_app_backend.serviceImpls.token;

import com.example.projecti_trello_app_backend.constants.SecurityConstants;
import com.example.projecti_trello_app_backend.entities.token.RefreshToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.token.RefreshTokenRepo;
import com.example.projecti_trello_app_backend.services.token.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenServiceImpl  implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;


    @Override
    public Optional<RefreshToken> findById(int id) {
        try{
            return refreshTokenRepo.findById(id);
        } catch (Exception ex)
        {
          log.error("Find refresh token by id error",ex);
          return Optional.empty();
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        try{
            return refreshTokenRepo.findByToken(token);
        } catch (Exception ex)
        {
            log.error("Find refresh token by token error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<RefreshToken> save(RefreshToken refreshToken) {
        try{
            refreshToken.setExpired(false);
            refreshToken.setExpirationTime(new Timestamp(System.currentTimeMillis()+ SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME));
            return Optional.ofNullable(refreshTokenRepo.save(refreshToken));
        } catch (Exception ex)
        {
            log.error("Save refresh token error", ex);
            return Optional.empty();
        }
    }

    @Override
    public String generateRefreshToken(User user) {
        try{
            RefreshToken refreshToken =  new RefreshToken();
            refreshToken.setUser(user);
            String token =Base64.getEncoder().encodeToString((user.getUserName()+user.getEmail()+ String.valueOf(System.currentTimeMillis())).getBytes());
            refreshToken.setToken(token);
            return save(refreshToken).isPresent()?token.toString():null;
        } catch (Exception ex)
        {
            log.error("Generate refresh token error",ex);
            return null;
        }
    }

    @Override
    public boolean verifyRefreshToken(String token) {
        try {
            RefreshToken refreshToken = refreshTokenRepo.findByToken(token).get();
            Date now = new Date(System.currentTimeMillis());
            if (refreshToken.getExpirationTime().after(now)) return true;
            else {
                setExpiredByToken(token);
                return false;
            }
        } catch (Exception ex)
        {
            log.error("Verify refresh token error",ex);
            return false;
        }
    }

    @Override
    public boolean setExpiredByToken(String token) {
        try{
            return refreshTokenRepo.setExpired(token)!=0?true:false;
        } catch (Exception ex)
        {
            log.error("Set refresh token expired error",ex);
            return false;
        }
    }
}
