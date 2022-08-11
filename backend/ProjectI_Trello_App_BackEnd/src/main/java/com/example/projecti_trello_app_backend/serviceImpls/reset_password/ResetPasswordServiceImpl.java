package com.example.projecti_trello_app_backend.serviceImpls.reset_password;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.entities.token.ResetPasswordToken;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.token.ResetPasswordTokenRepo;
import com.example.projecti_trello_app_backend.services.reset_password.ResetPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResetPasswordTokenRepo resetPasswordTokenRepo;

    @Override
    public Optional<ResetPasswordToken> findByToken(String token) {
        try{
            return resetPasswordTokenRepo.findByToken(token);
        }catch (Exception ex){
            log.error("find reset password token by tokenString error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean createResetPasswordToken(User user) {
        try {
            String createdTime = String.valueOf(System.currentTimeMillis());
            String stringToEncode = user.getEmail() + createdTime;
            String token = Base64.encodeBase64String(stringToEncode.getBytes(StandardCharsets.UTF_8));
            ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user, token);
            resetPasswordTokenRepo.save(resetPasswordToken);
            sendResetPasswordEmail(user,resetPasswordToken);
            return true;
        } catch (Exception ex)
        {
            log.error("create reset password token error",ex);
            return false;
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Optional<ResetPasswordToken> resetPasswordTokenOptional = resetPasswordTokenRepo.findByToken(token);
            if (!resetPasswordTokenOptional.isPresent()) return false;
            ResetPasswordToken resetPasswordToken = resetPasswordTokenOptional.get();
            System.out.println(resetPasswordToken.getExpireTime().getTime()+"and"+ System.currentTimeMillis());
           if(resetPasswordToken.getExpireTime().after(new Timestamp(System.currentTimeMillis())))
               return true;
           else{
               resetPasswordTokenRepo.setExpired(token);
               return false;
           }
        } catch (Exception ex)
        {
            log.error("validate token error",ex);
            return false;
        }
    }

    @Override
    public boolean setExpired(String token) {
        try{
            return resetPasswordTokenRepo.setExpired(token)!=0?true:false;
        }catch (Exception ex)
        {
            log.error("set resetToken Expired",ex);
            return false;
        }
    }

    @Override
    public boolean sendResetPasswordEmail(User user,ResetPasswordToken resetPasswordToken) { //send a email to confirm change password
      try {
          String toAddress = user.getEmail();
          String fromAddress = MailConstants.MAIL_SENDER;
          String subject = "Reset Password Email";
          String senderName = MailConstants.SENDER_NAME;
          MimeMessage message = mailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message);
          String content = "<div>Dear " +user.getFirstName()+" "+user.getLastName()+"<br>"+
                  "<h3>Thank for using our services!<br>Please click to under link to confirm reset password request </h3><br>"+
                  "<a href=\"" +MailConstants.USER_SITE_URL+"/reset-password" +"?reset_token="+resetPasswordToken.getToken()+
                  "\">Click to this link</a><br><br>"+
                  "Best regrads <br> Chien Dao</div>";
          helper.setTo(toAddress);
          helper.setFrom(fromAddress,senderName);
          helper.setSubject(subject);
          helper.setSentDate(new Date());
          helper.setText(content,true);
          mailSender.send(message);
          return true;
      }catch (Exception ex)
      {
          log.error("send email reset password error",ex);
          return false;
      }
    }
}
