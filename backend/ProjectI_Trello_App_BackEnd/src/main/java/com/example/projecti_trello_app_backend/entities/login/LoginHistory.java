package com.example.projecti_trello_app_backend.entities.login;

import com.example.projecti_trello_app_backend.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "${database.name}", name = "login_history")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loginId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "logedin_at")
    private Timestamp loginAt;

    @Column(name = "location")
    private String location;

    @Column(name = "device")
    private String device;
}
