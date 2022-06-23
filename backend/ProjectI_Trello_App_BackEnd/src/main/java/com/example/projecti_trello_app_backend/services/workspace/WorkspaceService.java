package com.example.projecti_trello_app_backend.services.workspace;

import com.example.projecti_trello_app_backend.dto.WorkSpaceDTO;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WorkspaceService {

    List<Workspace> findAll();

    Optional<Workspace> findByWorkspaceId(int workSpaceId);

    Optional<Workspace> add(Workspace workspace);

    Optional<Workspace> update(WorkSpaceDTO workSpaceDTO);

    boolean delete(int workSpaceId);
}
