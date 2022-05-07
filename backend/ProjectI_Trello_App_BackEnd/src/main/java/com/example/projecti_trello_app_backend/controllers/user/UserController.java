package com.example.projecti_trello_app_backend.controllers.user;

import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project1/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/find-by-username-email")
    public ResponseEntity<?> findByUserNameOrEmail(@RequestParam(name = "username",required = false) String userName,
                                                   @RequestParam(name = "email", required = false) String email)
    {
        return userService.findByUsernameOrEmail(userName,email).isPresent()
                ?ResponseEntity.ok(UserDTO.convertToDTO(userService.findByUsernameOrEmail(userName,email).get()))
                :ResponseEntity.ok(Optional.empty());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user)
    {
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
         return ResponseEntity.ok(userService.signUp(user));
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO,
                                    @PathVariable(name = "user_id") int userId)
    {
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.ok(Optional.empty());
        userDTO.setUserId(userId);
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassWord(@RequestBody UserDTO userDTO,
                                            @RequestParam("user_id") int userId)
    {
        if(!userService.findByUserId(userId).isPresent()) return ResponseEntity.status(404).build();
        User userToUpdate = userService.findByUserId(userId).get();
        if(passwordEncoder.matches(userDTO.getPassWord(),userToUpdate.getPassWord()))
            return ResponseEntity.status(304).build();
        else {
            userToUpdate.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
            return ResponseEntity.ok(userService.changePassWord(userToUpdate));
        }
    }
}
