package com.example.projecti_trello_app_backend.serviceImpls.user;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.token.ResetPasswordTokenRepo;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;

    @Override
    public List<User> findAll() {
        try{
            return  userRepo.findAll();

        } catch(Exception exp){
            exp.printStackTrace();
            return Collections.emptyList();
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
    public Optional<User> findByUserName(String userName) {
        try {
            return userRepo.findByUserName(userName);
        } catch (Exception ex)
        {
            log.error("find user by username error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return userRepo.findByEmail(email);
        } catch (Exception ex)
        {
            log.error("find user by email error",ex);
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
    public int existsByUsernameOrEmail(String userName, String email) {
        try {
            return userRepo.existsByUsernameOrEmail(userName,email);
        }catch (Exception ex)
        {
            log.error("check existing user by username or email error",ex);
            return -1;
        }
    }

    @Override
    public int existedUserNameOrEmail(String userName, String email) {
        try {
            return userRepo.existedUserNameOrEmail(userName,email);
        } catch (Exception ex)
        {
            log.error("check existed userName or email error");
            return -1;
        }
    }

    @Override
    public Optional<?> update(UserDTO userDTO) {
        try {
            if(!userRepo.findByUserId(userDTO.getUserId()).isPresent())
            {
                log.warn("User is not existed!");
                return Optional.empty();
            }
            User userToUpdate = userRepo.findByUserId(userDTO.getUserId()).get();
            userToUpdate.setSex(userDTO.getSex()!=null?userDTO.getSex():userToUpdate.getSex());
            userToUpdate.setFirstName(userDTO.getFirstName()!=null?userDTO.getFirstName():userToUpdate.getFirstName());
            userToUpdate.setLastName(userDTO.getLastName()!=null?userDTO.getLastName():userToUpdate.getLastName());
            userToUpdate.setAvatarUrl(userDTO.getAvatarUrl()!=null?userDTO.getAvatarUrl():userToUpdate.getAvatarUrl());
            userToUpdate.setPhoneNumber(userDTO.getPhoneNumber()!=null?userDTO.getPhoneNumber():userToUpdate.getPhoneNumber());
            userToUpdate.setRegion(userDTO.getRegion()!=null?userDTO.getRegion():userToUpdate.getRegion());
            return Optional.of(UserDTO.convertToDTO(userRepo.save(userToUpdate)));
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

    @Override
    public Optional<User> resetPassword(UserDTO userDTO, String token) {
        try {
            User userToUpDate = userRepo.findByUserId(userDTO.getUserId()).get();
            userToUpDate.setPassword(userDTO.getPassWord());
            Optional<User> updatedUserOptional = Optional.ofNullable(userRepo.save(userToUpDate));
            resetPasswordTokenRepo.setExpired(token);
            return updatedUserOptional;
        }catch (Exception ex)
        {
            log.error("reset password error",ex);
            return Optional.empty();
        }
    }
}
