package com.example.projecti_trello_app_backend.serviceImpls.auth;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> signUp(User user, String siteURL) { // create a new user
        try{
            if(userRepo.findByUserNameOrEmail(user.getUserName(),user.getEmail()).isPresent())
                return Optional.empty();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            String fromAddress = MailConstants.MAIL_SENDER;
            String subject ="Verification Email";
            String senderName = MailConstants.SENDER_NAME;
            StringBuilder content = new StringBuilder();
            content.append("<div>");
            content.append("<h2>Dear ").append(user.getFirstName()+" "+user.getLastName()).append(", Thank for using our service!</h2><br>");
            content.append("<h2>Please verificate your account by click to the below link:</h2><br>");
            content.append("<a href =\"").append(siteURL+"/verify?verification-code="+user.getVerificationCode())
                    .append("\">Activate your account</a><br>")
                    .append("<h2> Or you can also paste this code to do the same (Do not provide this code for anyone)" +
                            ":<br><h3>"+user.getVerificationCode()+"</h3><br>");
            content.append("<br><br>Best Regards!<br> Chien Dao</div>");
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
}
