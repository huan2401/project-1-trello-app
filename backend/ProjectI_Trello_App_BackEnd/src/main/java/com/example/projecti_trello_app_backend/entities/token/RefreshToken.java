package com.example.projecti_trello_app_backend.entities.token;

import com.example.projecti_trello_app_backend.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int  id ;

    @Column(name = "token", unique = true, nullable = false)
    @NotNull
    private String token;

    @Column(name = "expiration_time")
    private Timestamp expirationTime;

    @Column(name="expired")
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @NotNull
    private User user;
}
