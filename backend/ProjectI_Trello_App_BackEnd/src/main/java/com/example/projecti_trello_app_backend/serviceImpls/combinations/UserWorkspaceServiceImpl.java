package com.example.projecti_trello_app_backend.serviceImpls.combinations;

import com.example.projecti_trello_app_backend.dto.UserWorkspaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.repositories.combinations.UserWorkspaceRepo;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserWorkspaceServiceImpl implements UserWorkspaceService {

    @Autowired
    private UserWorkspaceRepo  userWorkspaceRepo;

    @Override
    public Optional<UserWorkspace> findById(int id) {
        try {
            return userWorkspaceRepo.findById(id);
        } catch (Exception ex)
        {
            log.error("find user workspace by id error",ex);
            return Optional.empty();
        }
    }

    @Override
    public List<UserWorkspace> findByUser(int userId) {
        try {
            return userWorkspaceRepo.findByUser(userId);
        }catch (Exception ex)
        {
            log.error("find user workspace by user error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public List<UserWorkspace> findByWorkspace(int workspaceId) {
        try {
            return userWorkspaceRepo.findByWorkspace(workspaceId);
        }catch (Exception ex)
        {
            log.error("find user workspace by workspace error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<UserWorkspace> findByUserAndWorkspace(int userId, int workspaceId) {
        try {
            return userWorkspaceRepo.findByUserAndWorkspace(userId,workspaceId);
        }catch (Exception ex)
        {
            log.error("find user workspace by user and workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean checkRole(int workspaceId, int userId,String roleName) {
        try{
            return userWorkspaceRepo.checkRole(workspaceId,userId,roleName)>0?true:false;
        } catch (Exception ex)
        {
            log.error("check workspace creator error", ex);
            return false;
        }
    }

    @Override
    public boolean existsByUserAndWorkspace(int userId, int workSpaceId) {
        try{
            return userWorkspaceRepo.existsByUserAndWorkspace(userId,workSpaceId);
        } catch (Exception ex)
        {
            log.error("Check existed user in workspace error",ex);
            return false;
        }
    }

    @Override
    public Optional<UserWorkspace> add(UserWorkspace userWorkspace) {
        try {
            return Optional.ofNullable(userWorkspaceRepo.save(userWorkspace));
        }catch (Exception ex)
        {
            log.error("add user workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserWorkspace> update(UserWorkspaceDTO userWorkspaceDTO) {
        try {
            if(!userWorkspaceRepo.findByUserAndWorkspace(userWorkspaceDTO.getUserId(),userWorkspaceDTO.getWorkspaceId()).isPresent())
                return Optional.empty();
            UserWorkspace userWorkspaceToUpDate = userWorkspaceRepo.findByUserAndWorkspace(
                    userWorkspaceDTO.getUserId(),userWorkspaceDTO.getWorkspaceId()).get();
            userWorkspaceToUpDate.setDeleted(userWorkspaceDTO.getDeleted()!=null?userWorkspaceDTO.getDeleted()
                                                                                :userWorkspaceToUpDate.isDeleted());
            return Optional.ofNullable(userWorkspaceRepo.save(userWorkspaceToUpDate));
        }catch (Exception ex)
        {
            log.error("update user workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            return userWorkspaceRepo.delete(id)!=0?true:false;
        }catch (Exception ex)
        {
            log.error("delete user workspace error", ex);
            return false;
        }
    }

    @Override
    public boolean deleteByWorkSpace(int workspaceId) {
        try {
            return userWorkspaceRepo.deleteByWorkspace(workspaceId)!=0?true:false;
        }catch (Exception ex)
        {
            log.error("delete user workspace by workspace", ex);
            return false;
        }
    }

    @Override
    public boolean removeUserFromWorkspace(int userId, int workspaceId) {
        try{
            if(!userWorkspaceRepo.findByUserAndWorkspace(userId,workspaceId).isPresent()) return false;
            return userWorkspaceRepo.removeUserFromWorkspace(userId,workspaceId)!=0?true:false;
        }catch (Exception ex)
        {
            log.error("remove user from workspace error",ex);
            return false;
        }
    }
}
