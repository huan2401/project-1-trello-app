package com.example.projecti_trello_app_backend.dto.loginDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {

   private String newAccessToken ;

   private String refreshToken;

   private Date refreshedAt;
    
}
