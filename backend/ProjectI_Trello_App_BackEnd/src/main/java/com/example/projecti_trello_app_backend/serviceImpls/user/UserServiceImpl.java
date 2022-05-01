package com.example.projecti_trello_app_backend.serviceImpls.user;

import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> findAll() {
        try{
            return  userRepo.findAll();

        } catch(Exception exp){
            exp.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Optional<User> findByUserId(int userId) {
        try{
            return userRepo.findByUserId(userId).isPresent()?userRepo.findByUserId(userId):Optional.empty();
        } catch (Exception exp){
            log.error("Find by UserId error",exp);
            exp.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String userName, String email) {
        try {
            return userRepo.findByUserNameOrEmail(userName,email).isPresent()?userRepo.findByUserNameOrEmail(userName,email)
                    :Optional.empty();
        } catch (Exception exp){
            log.error("Find by Username or Email error", exp);
             exp.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> signUp(User user) {
        try{
            if(userRepo.findByUserNameOrEmail(user.getUserName(),user.getEmail()).isPresent())
                return Optional.empty();
            return Optional.of(userRepo.save(user));
        } catch (Exception exp)
        {
            log.error("Sign Up error ",exp);
            exp.printStackTrace();
            return Optional.empty();
        }
    }
    @Override
    public Optional<User> update(UserDTO userDTO) {
        try {
            if(!userRepo.findByUserId(userDTO.getUserId()).isPresent())
            {
                log.warn("User is not existed!");
                return Optional.empty();
            }
            User userToUpdate = userRepo.findByUserId(userDTO.getUserId()).get();
            userToUpdate.setFirstName(userDTO.getFirstName()!=null?userDTO.getFirstName():userToUpdate.getFirstName());
            userToUpdate.setLastName(userDTO.getLastName()!=null?userDTO.getLastName():userToUpdate.getLastName());
            userToUpdate.setAvatarUrl(userDTO.getAvatarUrl()!=null?userDTO.getAvatarUrl():userToUpdate.getAvatarUrl());
            userToUpdate.setPhoneNumber(userDTO.getPhoneNumber()!=null?userDTO.getPhoneNumber():userToUpdate.getPhoneNumber());
            return Optional.of(userRepo.save(userToUpdate));
        } catch (Exception exp)
        {
            log.error("Update User error",exp);
            exp.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> changePassWord(User user) {
        try{
            return Optional.ofNullable(userRepo.save(user));
        } catch (Exception exp)
        {
            log.error("Change Password Error",exp);
            exp.printStackTrace();
            return Optional.empty();
        }
    }
}
