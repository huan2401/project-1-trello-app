package com.example.projecti_trello_app_backend.serviceImpls.workspace;

import com.example.projecti_trello_app_backend.dto.WorkSpaceDTO;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.repositories.workspace.WorkspaceRepo;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private WorkspaceRepo workspaceRepo;

    @Override
    public List<Workspace> findAll() {
        try{
            return workspaceRepo.findAll();
        } catch (Exception ex){
            log.error("find all workspace error",ex);
            return List.of();
        }
    }

    @Override
    public Optional<Workspace> findByWorkSpaceId(int workSpaceId) {
        try{
            Optional<Workspace> workspaceOptional = workspaceRepo.findByWorkSpaceId(workSpaceId);
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
            log.error("add workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Workspace> update(WorkSpaceDTO workSpaceDTO) {
        try{
            if(!workspaceRepo.findByWorkSpaceId(workSpaceDTO.getWorkspaceId()).isPresent())
                return Optional.empty();
            Workspace workspaceToUpdate = workspaceRepo.findByWorkSpaceId(workSpaceDTO.getWorkspaceId()).get();
            workspaceToUpdate.setWorkSpaceTitle(workSpaceDTO.getWorkSpaceTitle()!=null?
                    workSpaceDTO.getWorkSpaceTitle():workspaceToUpdate.getWorkSpaceTitle());
            workspaceToUpdate.setWorkSpaceDescription(workSpaceDTO.getWorkSpaceDescription()!=null?
                    workSpaceDTO.getWorkSpaceDescription(): workspaceToUpdate.getWorkSpaceDescription());
            workspaceToUpdate.setWorkSpaceType(workSpaceDTO.getWorkSpaceType()!=null?
                    workSpaceDTO.getWorkSpaceType(): workspaceToUpdate.getWorkSpaceType());
            return Optional.ofNullable(workspaceRepo.save(workspaceToUpdate));
        } catch (Exception ex) {
            log.error("update workspace error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int workSpaceId) {
        try {
            return workspaceRepo.deleteByWorkSpaceId(workSpaceId)>0?true:false;
        }catch (Exception ex)
        {
            log.error("delete workspace error",ex);
            return false;
        }
    }
}
