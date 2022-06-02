package com.example.projecti_trello_app_backend.repositories.token;

import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken,Integer>
{
    @Query("from ResetPasswordToken rsToken where rsToken.token =?1 and rsToken.expired =false")
    Optional<ResetPasswordToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("update ResetPasswordToken rsToken set rsToken.expired =true where rsToken.token=?1 and rsToken.expired=false")
    int setExpired(String token);
}
