package com.example.projecti_trello_app_backend.controllers.user;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.reset_password.ResetPasswordService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name = "User Controller")
@RestController
@RequestMapping("/project1/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Find a user by username or email")
    @GetMapping(path = "/find-by-username-email")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByUserNameOrEmail(@RequestParam(name = "username",required = false) String userName,
                                                   @RequestParam(name = "email", required = false) String email,
                                                   HttpServletRequest request)
    {
        if(email.contains("%4")) email =email.replace("%4","@");
        return userService.findByUsernameOrEmail(userName,email).isPresent()
                ?ResponseEntity.ok(UserDTO.convertToDTO(userService.findByUsernameOrEmail(userName,email).get()))
                :ResponseEntity.ok(Optional.empty());
    }


    @Operation(summary = "Update user's information")
    @PutMapping(path = "/update")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO,
                                    HttpServletRequest request)
    {
        int userId = util.getUserFromRequest(request).getUserId();
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.ok(Optional.empty());
        userDTO.setUserId(userId);
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @Operation(summary = "Change user's password")
    @PutMapping(path = "/change-password")
    public ResponseEntity<?> changePassWord(@RequestBody UserDTO userDTO,
                                            HttpServletRequest request)
    {
        int userId = util.getUserFromRequest(request).getUserId();
        if(!userService.findByUserId(userId).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("User not found",204));
        User userToUpdate = userService.findByUserId(userId).get();
        if(!passwordEncoder.matches(userDTO.getPreviousPassword(),userToUpdate.getPassword()))
            return ResponseEntity.status(204).body(new MessageResponse("Wrong current password",204));
        else if (passwordEncoder.matches(userDTO.getPassWord(),userToUpdate.getPassword()))
            return ResponseEntity.status(304).body(new MessageResponse("This Password is similar to the previous password," +
                    "please choose a new password",304));
        else {
            userToUpdate.setPassword(passwordEncoder.encode(userDTO.getPassWord()));
            return ResponseEntity.ok(userService.changePassWord(userToUpdate));
        }
    }

    @Operation(summary = "Reset user's password")
    @PostMapping(path = "/reset-password")
    public ResponseEntity<?> resetPasswordRequest(@RequestParam(name = "email") String email,
                                                  HttpServletRequest request)
    {
        if(email.contains("%4")) email =email.replace("%4","@");
        return userService.findByUsernameOrEmail("username",email).map(user ->
        {
           return resetPasswordService.createResetPasswordToken(user)
                   ?ResponseEntity.status(200).build()
                   :ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Handle reset user's password")
    @PutMapping(path = "/reset-password/reset")
    public ResponseEntity<?> resetPasswordHandle(@RequestParam(name = "reset_token") String token,
                                                 @RequestBody UserDTO userDTO,
                                                 HttpServletRequest request)
    {
        Optional<ResetPasswordToken> rsPasswordTokenOptional = resetPasswordService.findByToken(token);
        System.out.println(rsPasswordTokenOptional);
        if(rsPasswordTokenOptional.isPresent()==false || resetPasswordService.validateToken(token)==false) {
            return ResponseEntity.status(304).body(new MessageResponse("Not found token or token is invalid !",304));
        }
        else {
            userDTO.setUserId(rsPasswordTokenOptional.get().getUser().getUserId());
            userDTO.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
            return userService.resetPassword(userDTO,token).isPresent() ? ResponseEntity.status(200).build()
                    : ResponseEntity.status(304).body(new MessageResponse("Reset password fail",304));
        }
    }
}
