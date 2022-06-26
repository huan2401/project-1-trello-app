package com.example.projecti_trello_app_backend.utils;

import com.example.projecti_trello_app_backend.constants.SecurityConstants;
import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.repositories.combinations.UserBoardRoleRepo;
import com.example.projecti_trello_app_backend.repositories.combinations.UserTaskRepo;
import com.example.projecti_trello_app_backend.repositories.combinations.UserWorkspaceRepo;
import com.example.projecti_trello_app_backend.repositories.comment.CommentRepo;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.security.JWTProvider;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/*
* Security utils for checking permission when a api called
* */
@Component
@Slf4j
public class SecurityUtils {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserBoardRoleRepo userBoardRoleRepo;

    @Autowired
    private ColumnTaskService columnTaskService;

    @Autowired
    private UserWorkspaceRepo userWorkspaceRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private JWTProvider jwtProvider;


    String getTokenFromRequest(HttpServletRequest request)
    {
        String authHeader = request.getHeader(SecurityConstants.AUTH_HEADER);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(SecurityConstants.JWT_TYPE))
            return authHeader.substring(7);
        return null;
    }

    User getUserFromRequest(HttpServletRequest request)
    {
        String accessToken = getTokenFromRequest(request);
        if(accessToken==null) return null;
        String userName = jwtProvider.getUserNameFromJwt(accessToken);
        Optional<User> userOptional = userRepo.findByUserNameOrEmail(userName,"");
        return userOptional.isPresent()?userOptional.get():null;
    }

    String getUserBoardRole(HttpServletRequest request, int boardId){
        try {
            User user = getUserFromRequest(request);
            if (user == null) return "NOT_HAVE_PERMISSION";
            Optional<UserBoardRole> userBoardRoleOptional = userBoardRoleRepo.findByUserAndBoard(user.getUserId(), boardId);
            return userBoardRoleOptional.isPresent()
                    ? userBoardRoleOptional.get().getRole().getRoleName()
                    : "USER_NOT_IN_BOARD";
        } catch (Exception ex)
        {
            log.error("get UserBoard's role ",ex);
            return "NOT_HAVE_PERMISSION";
        }
    }

    String getUserWorkspaceRole(HttpServletRequest request, int workspaceId)
    {
        User user = getUserFromRequest(request);
        if(user==null) return "NOT_HAVE_PERMISSION";
        Optional<UserWorkspace> userWorkspaceOptional = userWorkspaceRepo.findByUserAndWorkspace(user.getUserId(),workspaceId);
        if(!userWorkspaceOptional.isPresent())
            return "WS_GUESS";
        try {
           return userWorkspaceOptional.get().getRole();
        }
        catch (Exception ex)
        {
            log.error("get UserWorkspace role ",ex);
            return "NOT_HAVE_PERMISSION";
        }
    }

    String getUserTaskRole(HttpServletRequest request, int taskId)
    {
        try {
            int boardId = columnTaskService.findAllByTask(taskId).get(0).getColumn().getBoard().getBoardId();
            User user = getUserFromRequest(request);
            if (user == null) return "NOT_HAVE_PERMISSION";
            Optional<UserBoardRole> userBoardRoleOptional = userBoardRoleRepo.findByUserAndBoard(user.getUserId(), boardId);
            return userBoardRoleOptional.isPresent()
                    ? userBoardRoleOptional.get().getRole().getRoleName()
                    : "USER_NOT_IN_BOARD";
        } catch (Exception ex)
        {
            log.error("get UserTask's role error",ex);
            return "NOT_HAVE_PERMISSION";
        }
    }

    boolean checkUserCommentRole(HttpServletRequest request,int commentId)
    {
        try {
            User user = getUserFromRequest(request);
            if (user == null) return false;
            return commentRepo.findByCommentId(commentId).get().getUser().getUserId() == user.getUserId();
        } catch (Exception ex)
        {
            log.error("get UserComment's role error", ex);
            return false;
        }

    }

}