package com.example.projecti_trello_app_backend.services.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public interface UserBoardRoleService {

    List<UserBoardRole> findById(int id);

    List<UserBoardRole> findByBoard(int boardId);

    List<UserBoardRole> findByUser(int userId);

    Optional<UserBoardRole> findByUserAndBoard(int userId, int boardId);

    Optional<UserBoardRole> add(UserBoardRole userBoardRole);

    boolean setRoleForUser(int userId, int boardId,String roleName);

    boolean deleteByBoard(int boardId);

    boolean deleteUserFromBoard(int userId, int boardId);


}
