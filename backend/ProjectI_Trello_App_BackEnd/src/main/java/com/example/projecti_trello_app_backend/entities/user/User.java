package com.example.projecti_trello_app_backend.entities.user;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "bfrtggj2gyfwccjjatxj", name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_id", unique = true)
    private int userId;

    @Column(name = "user_name", unique = true, nullable = false)
    @NotNull
    private String userName;

    @Column(name ="password", nullable = false)
    @NotNull
    private String passWord;

    @Column(name = "email",unique = true, nullable = false)
    @NotNull
    private String email;

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull
    private String lastName;

    @Column(name = "avatar", length = 256)
    private String avatarUrl;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

}
