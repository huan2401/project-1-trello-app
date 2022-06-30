package com.example.projecti_trello_app_backend.controllers.user;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.reset_password.ResetPasswordService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/project1/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @GetMapping(path = "/find-by-username-email")
    public ResponseEntity<?> findByUserNameOrEmail(@RequestParam(name = "username",required = false) String userName,
                                                   @RequestParam(name = "email", required = false) String email,
                                                   HttpServletRequest request)
    {
        return userService.findByUsernameOrEmail(userName,email).isPresent()
                ?ResponseEntity.ok(UserDTO.convertToDTO(userService.findByUsernameOrEmail(userName,email).get()))
                :ResponseEntity.ok(Optional.empty());
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUp(@RequestBody User user,
                                    HttpServletRequest request)
    {
        String siteURL = MailConstants.USER_SITE_URL;
        Optional<User> userOptional = userService.signUp(user,siteURL);
        return userOptional.isPresent()
                ?ResponseEntity.status(200).build()
                :ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<?> verifyUser(@RequestParam(name ="verification-code") String verificationCode,
                                        HttpServletRequest request)
    {
        Optional<User> verifiedUser = userService.verifyUser(verificationCode);
        return verifiedUser.isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Activate user successfully"))
                :ResponseEntity.status(204).body(new MessageResponse("Activate user fail"));
    }

    @PutMapping(path = "/update/{user_id}")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO,
                                    @PathVariable(name = "user_id") int userId,
                                    HttpServletRequest request)
    {
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.ok(Optional.empty());
        userDTO.setUserId(userId);
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PatchMapping(path = "/change-password")
    public ResponseEntity<?> changePassWord(@RequestBody UserDTO userDTO,
                                            @RequestParam("user_id") int userId,
                                            HttpServletRequest request)
    {
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.noContent().build();
        User userToUpdate = userService.findByUserId(userId).get();
        if(!userDTO.getPreviousPassword().equals(userDTO.getPreviousPassword()))
            return ResponseEntity.noContent().build();
        if(passwordEncoder.matches(userDTO.getPassWord(),userToUpdate.getPassword()))
            return ResponseEntity.status(304).build();
        else {
            userToUpdate.setPassword(passwordEncoder.encode(userDTO.getPassWord()));
            return ResponseEntity.ok(Optional.of(UserDTO.convertToDTO(userService.changePassWord(userToUpdate).get())));
        }
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<?> resetPasswordRequest(@RequestParam(name = "email") String email,
                                                  HttpServletRequest request)
    {
        return userService.findByUsernameOrEmail("username",email).map(user ->
        {
           return resetPasswordService.createResetPasswordToken(user)
                   ?ResponseEntity.status(200).build()
                   :ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.noContent().build());
    }

    @PutMapping(path = "/reset-password/reset")
    public ResponseEntity<?> resetPasswordHandle(@RequestParam(name = "reset_token") String token,
                                                 @RequestBody UserDTO userDTO,
                                                 HttpServletRequest request)
    {
        Optional<ResetPasswordToken> rsPasswordTokenOptional = resetPasswordService.findByToken(token);
        System.out.println(rsPasswordTokenOptional);
        if(rsPasswordTokenOptional.isPresent()==false || resetPasswordService.validateToken(token)==false) {
            return ResponseEntity.status(204).body(new MessageResponse("Not found token or token is invalid !"));
        }
        else {
            userDTO.setUserId(rsPasswordTokenOptional.get().getUser().getUserId());
            userDTO.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
            return userService.resetPassword(userDTO,token).isPresent() ? ResponseEntity.status(200).build()
                    : ResponseEntity.status(204).body(new MessageResponse("Reset password fail"));
        }
    }
}
