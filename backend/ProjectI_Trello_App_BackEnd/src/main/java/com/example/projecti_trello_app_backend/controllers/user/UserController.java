package com.example.projecti_trello_app_backend.controllers.user;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.token.ResetPasswordService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.utils.HttpUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
                                                   @RequestParam(name = "email", required = false) String email)
    {
        return userService.findByUsernameOrEmail(userName,email).isPresent()
                ?ResponseEntity.ok(UserDTO.convertToDTO(userService.findByUsernameOrEmail(userName,email).get()))
                :ResponseEntity.ok(Optional.empty());
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signUp(@RequestBody User user)
    {
        String siteURL = MailConstants.USER_SITE_URL;
        Optional<User> userOptional = userService.signUp(user,siteURL);
        return userOptional.isPresent()?ResponseEntity.status(200).build():ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<?> verifyUser(@RequestParam(name ="verify-code") String verifyCode)
    {
        return userService.verifyUser(verifyCode).isPresent()
                ?ResponseEntity.ok(UserDTO.convertToDTO(userService.verifyUser(verifyCode).get()))
                :ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update/{user_id}")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO,
                                    @PathVariable(name = "user_id") int userId)
    {
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.ok(Optional.empty());
        userDTO.setUserId(userId);
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PatchMapping(path = "/change-password")
    public ResponseEntity<?> changePassWord(@RequestBody UserDTO userDTO,
                                            @RequestParam("user_id") int userId)
    {
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.noContent().build();
        User userToUpdate = userService.findByUserId(userId).get();
        if(!userDTO.getPreviousPassword().equals(userDTO.getPreviousPassword()))
            return ResponseEntity.noContent().build();
        if(passwordEncoder.matches(userDTO.getPassWord(),userToUpdate.getPassWord()))
            return ResponseEntity.status(304).build();
        else {
            userToUpdate.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
            return ResponseEntity.ok(Optional.of(UserDTO.convertToDTO(userService.changePassWord(userToUpdate).get())));
        }
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<?> resetPasswordRequest(@RequestParam(name = "email") String email)
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
                                                 @RequestBody UserDTO userDTO)
    {
        Optional<ResetPasswordToken> rsPasswordTokenOptional = resetPasswordService.findByToken(token);
        if(!rsPasswordTokenOptional.isPresent() ||resetPasswordService.validateToken(token)==false)
            return ResponseEntity.ok("not find token or validate fail");
        userDTO.setUserId(rsPasswordTokenOptional.get().getUser().getUserId());
        userDTO.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
        resetPasswordService.setExpired(token);
        return userService.resetPassword(userDTO).isPresent()?ResponseEntity.status(200).build()
                                                            : ResponseEntity.noContent().build();
    }
}
