package com.example.projecti_trello_app_backend.controllers.auth;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.dto.loginDTO.JWTResponse;
import com.example.projecti_trello_app_backend.dto.loginDTO.LoginRequest;
import com.example.projecti_trello_app_backend.dto.loginDTO.RefreshTokenRequest;
import com.example.projecti_trello_app_backend.dto.loginDTO.RefreshTokenResponse;
import com.example.projecti_trello_app_backend.entities.token.RefreshToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.exception_handler.exceptions.TokenRefreshInvalidException;
import com.example.projecti_trello_app_backend.security.user_detail.CustomUserDetailService;
import com.example.projecti_trello_app_backend.security.user_detail.CustomUserDetails;
import com.example.projecti_trello_app_backend.security.jwt.JWTProvider;
import com.example.projecti_trello_app_backend.services.auth.AuthService;
import com.example.projecti_trello_app_backend.services.token.RefreshTokenService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Tag(name = "Authentication Controller")
@RestController
@RequestMapping("/project1/api/auth")
public class AuthenticationController {

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

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login to get access token and refresh token")
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
                throw new BadCredentialsException("Wrong username/email or password !");
            }
            String accessToken = jwtProvider.generateAccessToken(userDetails.getUser());
            String refreshToken = refreshTokenService.generateRefreshToken(userDetails.getUser());
            if(refreshToken==null) return ResponseEntity.status(204).body(new MessageResponse("Generate refresh token fail",204));
            return ResponseEntity.ok(new JWTResponse(accessToken,
                                                    refreshToken,
                                            "Bearer",
                                                    UserDTO.convertToDTO(userDetails.getUser()),
                                                    jwtProvider.getClaims(accessToken).getIssuedAt(),
                                                    jwtProvider.getClaims(accessToken).getExpiration()));
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong username/email or password !",400));
        }
    }

    @Operation(summary = "Refresh access token by refresh token")
    @PostMapping(path = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest,
                                          HttpServletRequest request)
    {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
        if(!refreshTokenOptional.isPresent() || !refreshTokenService.verifyRefreshToken(refreshToken))
            throw new TokenRefreshInvalidException(refreshToken,"Please login again to get new refresh token");
        String newAccessToken = jwtProvider.generateAccessToken(refreshTokenOptional.get().getUser());
        return ResponseEntity.ok(new RefreshTokenResponse(newAccessToken,refreshToken,new Date()));
    }

    @Operation(summary = "Signup a new user")
    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUp(@RequestBody User user,
                                    HttpServletRequest request)
    {
        try {
            if(userService.existedUserNameOrEmail(user.getUserName(), user.getEmail())>0)
                return ResponseEntity.status(204).body(new MessageResponse("Username or Email is existed!",204));
            String url = request.getRequestURL().toString();
            String siteURL = url.substring(0, url.length() - 7); // get to request url
            Optional<User> userOptional = authService.signUp(user, siteURL);
            return userOptional.isPresent()
                    ? ResponseEntity.status(200).body( new MessageResponse("Signup successfully, please go to your email to activate your account",200))
                    : ResponseEntity.status(204).body(new MessageResponse("Signup fail",204));
        } catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage(),400));
        }
    }

    @Operation(summary = "Acticate a user thourgh a link sent in an email")
    @GetMapping(path = "/verify-by-link")
    public ResponseEntity<?> verifyUserByLink(@RequestParam(name ="verification-code") String verificationCode,
                                              HttpServletRequest request)
    {
        Optional<User> verifiedUser = authService.verifyUser(verificationCode);
        return verifiedUser.isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Activate user successfully",200))
                :ResponseEntity.status(204).body(new MessageResponse("Activate user fail",204));
    }

    @Operation(summary = "Acticate a user thourgh a code sent in an email")
    @GetMapping("/verify-by-code")
    public ResponseEntity<?> verifyByVerificationCode(@RequestHeader(name = "verificationCode") String verificationCode,
                                                      HttpServletRequest request)
    {
        Optional<User> verifiedUser = authService.verifyUser(verificationCode);
        return verifiedUser.isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Activate user successfully",200))
                :ResponseEntity.status(204).body(new MessageResponse("Activate user fail",204));
    }

}
