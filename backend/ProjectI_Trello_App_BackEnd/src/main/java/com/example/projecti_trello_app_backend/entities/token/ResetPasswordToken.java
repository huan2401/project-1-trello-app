package com.example.projecti_trello_app_backend.entities.token;

import com.example.projecti_trello_app_backend.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}",name = "reset_password_token")
public class ResetPasswordToken {

    public static final long EXPIRE_DURATION = 180000; // expire duration = 180s

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token", unique = true, nullable = false,length = 64)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expire_time")
    private Timestamp expireTime;

    @Column(name = "expired")
    private Boolean expired;

    public ResetPasswordToken(User user, String token)
    {
        this.user =user;
        this.token =token;
        this.expireTime = new Timestamp(System.currentTimeMillis() +EXPIRE_DURATION);
        this.expired =false;
    }
}
