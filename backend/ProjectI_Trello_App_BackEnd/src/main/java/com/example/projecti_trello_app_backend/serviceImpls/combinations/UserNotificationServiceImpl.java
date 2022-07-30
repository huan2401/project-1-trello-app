package com.example.projecti_trello_app_backend.serviceImpls.combinations;

import com.example.projecti_trello_app_backend.constants.MailConstants;
import com.example.projecti_trello_app_backend.entities.combinations.UserNotification;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.combinations.UserNotificationRepo;
import com.example.projecti_trello_app_backend.services.combinations.UserNotificationService;
import com.example.projecti_trello_app_backend.utils.EmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepo userNotificationRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Optional<UserNotification> findById(int id) {
        try{
            return userNotificationRepo.findById(id);
        }catch (Exception ex)
        {
            log.error("find User Notification error", ex);
            return Optional.empty();
        }
    }

    @Override
    public List<UserNotification> findByUser(int userId) {
        try {
            return userNotificationRepo.findByUser(userId);
        } catch (Exception ex)
        {
            log.error("find UserNotification by User",ex);
            return Collections.emptyList();
        }
    }

    // add new UserNotification to table
    @Override
    public Optional<UserNotification> sendNotification(UserNotification userNotification) {
        try{
            userNotification.setSentAt(new Timestamp(System.currentTimeMillis()));
            userNotification.setRead(false);
            return Optional.ofNullable(userNotificationRepo.save(userNotification));
        } catch (Exception ex)
        {
            log.error("send notification error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean sendNotificationEmail(User byUser, User targetUser, Object obj, String emailType) {
        try{
            String toAddress =targetUser.getEmail();
            String fromAddress = MailConstants.MAIL_SENDER;
            String subject ="Verification Email";
            String senderName = MailConstants.SENDER_NAME;
            String content = new String();
            switch (emailType) {
                case "add" :{
                    content = EmailTemplate.addTemplate(byUser,targetUser,obj);
                    break;
                }
                case "update" :{
                    content = EmailTemplate.updateTemplate(byUser,targetUser,obj);
                    break;
                }
                default: content = EmailTemplate.removeTemplate(byUser,targetUser,obj);
            }
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromAddress,senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
            return true;
        } catch (Exception exception)
        {
            log.error("Send notification email error",exception);
            exception.printStackTrace();
            return false;
        }
    }
}
