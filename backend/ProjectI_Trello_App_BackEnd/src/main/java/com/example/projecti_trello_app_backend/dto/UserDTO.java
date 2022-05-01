package com.example.projecti_trello_app_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int userId;

    private String passWord;

    private String firstName;

    private String lastName;

    private String avatarUrl;

    private String phoneNumber;
}
