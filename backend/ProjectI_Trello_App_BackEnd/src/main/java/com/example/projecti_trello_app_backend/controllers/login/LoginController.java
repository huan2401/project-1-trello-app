package com.example.projecti_trello_app_backend.controllers.login;

import com.auth0.jwt.JWT;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.loginDTO.JWTResponse;
import com.example.projecti_trello_app_backend.dto.loginDTO.LoginRequest;
import com.example.projecti_trello_app_backend.security.CustomUserDetailService;
import com.example.projecti_trello_app_backend.security.CustomUserDetails;
import com.example.projecti_trello_app_backend.security.JWTProvider;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project1/api")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JWTProvider jwtProvider;

    @PostMapping(path = "/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailService.loadUserByUsername(loginRequest.getUserName());
            if(userDetails == null) {
                System.out.println("Error at this point");
               // throw new BadCredentialsException("Wrong username or email!");
            }
            String accessToken = jwtProvider.generateToken(userDetails);
            return ResponseEntity.ok(new JWTResponse(accessToken,
                                            "Bearer",
                                                    jwtProvider.getClaims(accessToken).getIssuedAt(),
                                                    jwtProvider.getClaims(accessToken).getExpiration()));
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong username or email !"));
        }
    }

    @GetMapping(path ="/login")
    public void login(){
        System.out.println("Login");
    }
}
