package com.example.projecti_trello_app_backend.dto;

import com.example.projecti_trello_app_backend.entities.user.User;
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

    private String userName;

    private String passWord;

    private String firstName;

    private String lastName;

    private String sex;

    private String avatarUrl;

    private String phoneNumber;

    public static UserDTO convertToDTO(User user)
    {
        return UserDTO.builder().userId(user.getUserId())
                                .userName(user.getUserName())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .sex(user.getSex())
                                .passWord("Private Field")
                                .avatarUrl(user.getAvatarUrl())
                                .phoneNumber(user.getPhoneNumber()).build();
    }
}
