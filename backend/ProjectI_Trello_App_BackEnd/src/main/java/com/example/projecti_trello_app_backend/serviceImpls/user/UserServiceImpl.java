package com.example.projecti_trello_app_backend.serviceImpls.user;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.dto.UserDTO;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
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
    public Optional<User> signUp(User user,String siteURL) { // create a new user
        try{
            if(userRepo.findByUserNameOrEmail(user.getUserName(),user.getEmail()).isPresent())
                return Optional.empty();
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            user.setActivated(false);
            User newUser = userRepo.save(user);
            sendVerificationEmail(user,siteURL);
            return Optional.ofNullable(newUser);
        } catch (Exception exp)
        {
            log.error("Sign Up error ",exp);
            return Optional.empty();
        }
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) // verify user -> set activated = true
    {
        try{
            String toAddress =user.getEmail();
            String fromAddress =MailConstants.MAIL_SENDER;
            String subject ="Verification Email";
            String senderName = MailConstants.SENDER_NAME;
            StringBuilder content = new StringBuilder();
            content.append("<div>");
            content.append("Dear ").append(user.getFirstName()+" "+user.getLastName()).append("! Thank for using our service<br>");
            content.append("<h2>Please verificate your account</h3><br>");
            content.append("<a href =\"").append(siteURL+"/verify?verify-code="+user.getVerificationCode())
                    .append("\">Activate your account</a><br>");
            content.append("Best Regards !<br> Chien Dao</div>");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(toAddress);
            helper.setFrom(fromAddress,senderName);
            helper.setSubject(subject);
            helper.setText(content.toString(),true); // set format content to html type
            mailSender.send(message);
        } catch (Exception ex)
        {
            log.error("send verification email error",ex);
        }
    }

    @Override
    public Optional<User> verifyUser(String verificationCode) {
        try{
            if(!userRepo.findUserByVerificationCode(verificationCode).isPresent()) return Optional.empty();
            User userToActivate = userRepo.findUserByVerificationCode(verificationCode).get();
            if(userToActivate.getActivated()==true) return Optional.empty();
            userToActivate.setActivated(true);
            return Optional.ofNullable(userRepo.save(userToActivate));
        } catch (Exception ex)
        {
            log.error("activate user error",ex);
            return Optional.empty();
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
}
