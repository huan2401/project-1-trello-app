package com.example.projecti_trello_app_backend.dto.loginDTO;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse {

    private String accessToken;

    private String tokenType ;

    private String userName;

    private String email;

}
