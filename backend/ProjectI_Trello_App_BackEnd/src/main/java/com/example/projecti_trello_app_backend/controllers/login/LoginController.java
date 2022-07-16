package com.example.projecti_trello_app_backend.controllers.login;

import com.auth0.jwt.JWT;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.loginDTO.JWTResponse;
import com.example.projecti_trello_app_backend.dto.loginDTO.LoginRequest;
import com.example.projecti_trello_app_backend.dto.loginDTO.RefreshTokenRequest;
import com.example.projecti_trello_app_backend.dto.loginDTO.RefreshTokenResponse;
import com.example.projecti_trello_app_backend.entities.token.RefreshToken;
import com.example.projecti_trello_app_backend.security.CustomUserDetailService;
import com.example.projecti_trello_app_backend.security.CustomUserDetails;
import com.example.projecti_trello_app_backend.security.JWTProvider;
import com.example.projecti_trello_app_backend.services.token.RefreshTokenService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/project1/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated LoginRequest loginRequest,
                                              HttpServletRequest request){
        try {
            String loginInAcc = loginRequest.getUserName()!=null && !loginRequest.getUserName().equals("string")
                    ?loginRequest.getUserName():loginRequest.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInAcc,loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get UserDetails by userName or email
           // CustomUserDetails userDetails = (CustomUserDetails) userDetailService.loadUserByUsername(loginInAcc);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            if(userDetails == null) {
                System.out.println("Error at this point");
                throw new BadCredentialsException("Wrong username/email or password !");
            }
            String accessToken = jwtProvider.generateAccessToken(userDetails.getUser());
            String refreshToken = refreshTokenService.generateRefreshToken(userDetails.getUser());
            if(refreshToken==null) return ResponseEntity.status(204).body(new MessageResponse("Generate Refresh token fail"));
            return ResponseEntity.ok(new JWTResponse(accessToken,
                                                    refreshToken,
                                            "Bearer",
                                                    jwtProvider.getClaims(accessToken).getIssuedAt(),
                                                    jwtProvider.getClaims(accessToken).getExpiration()));
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong username/email or password !"));
        }
    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest,
                                          HttpServletRequest request)
    {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
        if(!refreshTokenOptional.isPresent() || !refreshTokenService.verifyRefreshToken(refreshToken))
            return ResponseEntity.status(204).body(new MessageResponse("Refresh token is not valid"));
        String newAccessToken = jwtProvider.generateAccessToken(refreshTokenOptional.get().getUser());
        return ResponseEntity.ok(new RefreshTokenResponse(newAccessToken,refreshToken,new Date()));
    }
}
