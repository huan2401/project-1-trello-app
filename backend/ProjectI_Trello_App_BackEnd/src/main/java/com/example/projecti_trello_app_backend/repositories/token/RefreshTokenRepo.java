package com.example.projecti_trello_app_backend.repositories.token;

import com.example.projecti_trello_app_backend.entities.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

    @Query(value = "from RefreshToken  rfToken where  rfToken.id =?1 and rfToken.expired =false ")
    Optional<RefreshToken> findById(int id);

    @Query(value = "from RefreshToken rfToken where rfToken.token =?1 and rfToken.expired = false")
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "update RefreshToken rfToken set rfToken.expired =true " +
            " where rfToken.token = ?1 and rfToken.expired =false")
    int setExpired(String token);

}
