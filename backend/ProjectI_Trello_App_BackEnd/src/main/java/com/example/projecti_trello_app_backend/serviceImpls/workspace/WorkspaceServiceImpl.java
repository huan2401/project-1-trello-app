package com.example.projecti_trello_app_backend.serviceImpls.workspace;

import com.example.projecti_trello_app_backend.dto.WorkSpaceDTO;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.repositories.board.BoardRepo;
import com.example.projecti_trello_app_backend.repositories.combinations.UserWorkspaceRepo;
import com.example.projecti_trello_app_backend.repositories.workspace.WorkspaceRepo;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private WorkspaceRepo workspaceRepo;

    @Autowired
    private UserWorkspaceRepo userWorkspaceRepo;

    @Autowired
    private BoardRepo boardRepo;

    @Override
    public List<Workspace> findAll() {
        try{
            return workspaceRepo.findAll();
        } catch (Exception ex){
            log.error("find all workspace error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Workspace> findByWorkspaceId(int workSpaceId) {
        try{
            Optional<Workspace> workspaceOptional = workspaceRepo.findByWorkspaceId(workSpaceId);
            return workspaceOptional.isPresent()?workspaceOptional:Optional.empty();
        } catch (Exception ex)
        {
            log.error("find workspace by id error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Workspace> add(Workspace workspace) {
        try{
            return Optional.ofNullable(workspaceRepo.save(workspace));
        } catch (Exception ex)
        {
            ex.printStackTrace();
            log.error("add workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Workspace> update(WorkSpaceDTO workSpaceDTO) {
        try{
            if(!workspaceRepo.findByWorkspaceId(workSpaceDTO.getWorkspaceId()).isPresent())
                return Optional.empty();
            Workspace workspaceToUpdate = workspaceRepo.findByWorkspaceId(workSpaceDTO.getWorkspaceId()).get();
            workspaceToUpdate.setWorkspaceTitle(workSpaceDTO.getWorkspaceTitle()!=null?
                    workSpaceDTO.getWorkspaceTitle():workspaceToUpdate.getWorkspaceTitle());
            workspaceToUpdate.setWorkspaceDescription(workSpaceDTO.getWorkspaceDescription()!=null?
                    workSpaceDTO.getWorkspaceDescription(): workspaceToUpdate.getWorkspaceDescription());
            workspaceToUpdate.setWorkspaceType(workSpaceDTO.getWorkspaceType()!=null?
                    workSpaceDTO.getWorkspaceType(): workspaceToUpdate.getWorkspaceType());
            return Optional.ofNullable(workspaceRepo.save(workspaceToUpdate));
        } catch (Exception ex) {
            log.error("update workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int workSpaceId) {
        try {
            return workspaceRepo.deleteByWorkspaceId(workSpaceId)>0
                    && userWorkspaceRepo.deleteByWorkspace(workSpaceId)>0
                    && boardRepo.deleteByWorkspace(workSpaceId)>0
                    ?true:false;
        }catch (Exception ex)
        {
            log.error("delete workspace error",ex);
            return false;
        }
    }
}
