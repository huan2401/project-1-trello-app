package com.example.projecti_trello_app_backend.controllers.workspace;

import com.example.projecti_trello_app_backend.dto.WorkSpaceDTO;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project1/api/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @GetMapping("/find-by-workspace-id")
    public ResponseEntity<?> findWorkSpaceById(@RequestParam(name = "workspace_id")int workSpaceId)
    {
        return workspaceService.findByWorkspaceId(workSpaceId).isPresent()
                ?ResponseEntity.ok(workspaceService.findByWorkspaceId(workSpaceId))
                :ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Workspace workspace,
                                 @RequestParam(name = "creator_id") int creatorId)
    {
        return userService.findByUserId(creatorId).map(creator -> {
            workspace.setWorkspaceCreator(creator);
            return ResponseEntity.ok(workspaceService.add(workspace));
        }).orElse(ResponseEntity.noContent().build());
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody WorkSpaceDTO workSpaceDTO,
                                    @RequestParam(name = "workspace_id") int workSpaceId)
    {
        return workspaceService.findByWorkspaceId(workSpaceId).map(workspace ->
        {
           workSpaceDTO.setWorkspaceId(workSpaceId);
           return ResponseEntity.ok(workspaceService.update(workSpaceDTO));
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "workspace_id") int workSpaceId)
    {
        return workspaceService.delete(workSpaceId)?ResponseEntity.status(200).build()
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
