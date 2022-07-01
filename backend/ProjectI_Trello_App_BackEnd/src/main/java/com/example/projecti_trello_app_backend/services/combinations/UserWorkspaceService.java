package com.example.projecti_trello_app_backend.services.combinations;

import com.example.projecti_trello_app_backend.dto.UserWorkspaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserWorkspaceService {

    Optional<UserWorkspace> findById(int id);

    List<UserWorkspace> findByUser(int userId);

    List<UserWorkspace> findByWorkspace(int workspaceId);

    Optional<UserWorkspace> findByUserAndWorkspace(int userId, int workspaceId);

    boolean checkRole(int workspaceId, int userId, String roleName);

    boolean existsByUserAndWorkspace(int userId, int workSpaceId);

    Optional<UserWorkspace> add(UserWorkspace userWorkspace);// add a new user to workspace

    Optional<UserWorkspace> update (UserWorkspaceDTO userWorkspaceDTO);

    boolean delete(int id);

    boolean deleteByWorkSpace(int workspaceId);

    boolean removeUserFromWorkspace(int userId,int workspaceId); // remove a user from a workspace

}
