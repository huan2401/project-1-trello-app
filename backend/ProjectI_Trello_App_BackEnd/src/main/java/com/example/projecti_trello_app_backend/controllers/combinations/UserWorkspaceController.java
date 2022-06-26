package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserWorkspaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project1/api/user-workspace")
public class UserWorkspaceController {

    @Autowired
    private UserWorkspaceService userWorkspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping(path = "/find-by-user")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id") int userId)
    {
        return userWorkspaceService.findByUser(userId).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("This user doesn't belong to any board"))
                :ResponseEntity.ok(userWorkspaceService.findByWorkspace(userId));
    }

    @GetMapping(path = "/find-by-workspace")
    public ResponseEntity<?> findByWorkspace(@RequestParam(name = "workspace_id")int workspaceId)
    {
        return userWorkspaceService.findByWorkspace(workspaceId).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("This workspace doesn't has any member"))
                :ResponseEntity.ok(userWorkspaceService.findByWorkspace(workspaceId));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add (@RequestParam(name = "user_id") int userId,
                                  @RequestParam(name = "workspace_id")int workspaceId)
    {
        if(userWorkspaceService.findByUserAndWorkspace(userId,workspaceId).isPresent())
            return ResponseEntity.noContent().build(); // existed a user workspace
        UserWorkspace userWorkspace = new UserWorkspace();
        userWorkspace.setRole("WS_MEMBER");
        return userService.findByUserId(userId).map(user -> {
            userWorkspace.setUser(user);
            return workspaceService.findByWorkspaceId(workspaceId).map(workspace -> {
                userWorkspace.setWorkspace(workspace);
                return ResponseEntity.ok(userWorkspaceService.add(userWorkspace));
            }).orElse(ResponseEntity.noContent().build());
        }).orElse(ResponseEntity.noContent().build());
    }


    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody UserWorkspaceDTO userWorkspaceDTO)
    {
           return ResponseEntity.ok("");
    }

    @DeleteMapping(path = "/delete-by-workspace")
    public ResponseEntity<?> deleteByWorkspace(@RequestParam(name = "workspace_id") int workspaceId)
    {
        return userWorkspaceService.deleteByWorkSpace(workspaceId)?ResponseEntity.status(200).build()
                                                                  :ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }


    @DeleteMapping(path = "/remove-user-from-workspace")
    public ResponseEntity<?> removeUserFromWorkspace(@RequestParam(name = "user_id") int userId,
                                                     @RequestParam(name = "workspace_id") int workspaceId)
    {
        if(userWorkspaceService.checkCreator(workspaceId,userId)==true)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        return userWorkspaceService
                .removeUserFromWorkspace(userId,workspaceId)
                ?ResponseEntity.status(200).build()
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
