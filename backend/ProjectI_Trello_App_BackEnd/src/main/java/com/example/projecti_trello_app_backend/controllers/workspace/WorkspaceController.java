package com.example.projecti_trello_app_backend.controllers.workspace;

import com.example.projecti_trello_app_backend.dto.WorkSpaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project1/api/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserWorkspaceService userWorkspaceService;

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
        Optional<Workspace> userWorkspaceToAdd = workspaceService.add(workspace);
        if(!userWorkspaceToAdd.isPresent()) return ResponseEntity.noContent().build();
        UserWorkspace userWorkspace = new UserWorkspace();
        return userService.findByUserId(creatorId).map(user -> {
            userWorkspace.setUser(user);
            userWorkspace.setWorkspace(userWorkspaceToAdd.get());
            userWorkspace.setRole("WS_CREATOR");
            return userWorkspaceService.add(userWorkspace).isPresent()?ResponseEntity.status(200).build()
                    :ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.noContent().build());

    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody WorkSpaceDTO workSpaceDTO,
                                    @RequestParam(name = "workspace_id") int workSpaceId,
                                    @RequestParam(name = "creator_id") int creatorId)
    {
        if(userWorkspaceService.checkCreator(workSpaceId,creatorId)==false)
            return ResponseEntity.noContent().build();
        return workspaceService.findByWorkspaceId(workSpaceId).map(workspace ->
        {
           workSpaceDTO.setWorkspaceId(workSpaceId);
           return ResponseEntity.ok(workspaceService.update(workSpaceDTO));
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "workspace_id") int workSpaceId,
                                    @RequestParam(name = "creator_id") int creatorId)
    {

        return workspaceService.delete(workSpaceId)
                && userWorkspaceService.checkCreator(workSpaceId,creatorId)
                ?ResponseEntity.status(200).build()
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
