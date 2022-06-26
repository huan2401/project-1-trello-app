package com.example.projecti_trello_app_backend.serviceImpls.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import com.example.projecti_trello_app_backend.repositories.combinations.UserBoardRoleRepo;
import com.example.projecti_trello_app_backend.services.combinations.UserBoardRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserBoardRoleServiceImpl implements UserBoardRoleService {

    @Autowired
    private UserBoardRoleRepo userBoardRoleRepo;

    @Override
    public List<UserBoardRole> findById(int id) {
        try{
            return userBoardRoleRepo.findById(id);
        } catch (Exception ex)
        {
            log.error("find UserBoardRole by id error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public List<UserBoardRole> findByBoard(int boardId) {
        try{
            return userBoardRoleRepo.findByBoard(boardId);
        } catch (Exception ex)
        {
            log.error("find UserBoardRole by board error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public List<UserBoardRole> findByUser(int userId) {
        try{
            return userBoardRoleRepo.findByUser(userId);
        } catch (Exception ex)
        {
            log.error("find UserBoardRole by user",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<UserBoardRole> findByUserAndBoard(int userId, int boardId) {
        try{
            return userBoardRoleRepo.findByUserAndBoard(userId, boardId);
        } catch (Exception ex)
        {
            log.error("find UserBoardRole by both user and board ",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserBoardRole> add(UserBoardRole userBoardRole) {
        try {
            return Optional.ofNullable(userBoardRoleRepo.save(userBoardRole));
        } catch (Exception ex)
        {
            log.error("add UserBoardRole error", ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean setRoleForUser(int userId,int boardId, String roleName) {
        try {
            return userBoardRoleRepo.setRoleForUser(userId,boardId,roleName)>0?true:false;
        } catch (Exception ex)
        {
            log.error("set role for user error",ex);
            return false;
        }
    }

    @Override
    public boolean deleteByBoard(int boardId) {
        try {
            return userBoardRoleRepo.deleteByBoard(boardId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete UserBoardRole by Board",ex);
            return false;
        }
    }

    @Override
    public boolean deleteUserFromBoard(int userId, int boardId) {
        try{
            return userBoardRoleRepo.deleteUserFromBoard(userId,boardId)>0
                    && userBoardRoleRepo.deleteByBoard(boardId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete User from Board error",ex);
            return false;
        }
    }
}
