package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserWorkspaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.security.authorization.RequireWorkspaceCreator;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name = "UserWorkspace Controller",
description = "Use to represent the relationship between users and workspaces with diffenrent role")
@RestController
@RequestMapping("/project1/api/user-workspace")
public class UserWorkspaceController {

    @Autowired
    private UserWorkspaceService userWorkspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Find all user_workspaces of a user")
    @GetMapping(path = "/find-by-user")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByUser(HttpServletRequest request)
    {
        int userId = util.getUserFromRequest(request).getUserId();
        return userWorkspaceService.findByUser(userId).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("This user doesn't belong to any workspace",204))
                :ResponseEntity.ok(userWorkspaceService.findByUser(userId));
    }

    @Operation(summary = "Find all user_workspace_s of a workspace")
    @GetMapping(path = "/find-by-workspace")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByWorkspace(@RequestParam(name = "workspace_id")int workspaceId,
                                             HttpServletRequest request)
    {
        return userWorkspaceService.findByWorkspace(workspaceId).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("This workspace doesn't has any member",204))
                :ResponseEntity.ok(userWorkspaceService.findByWorkspace(workspaceId));
    }

    @Operation(summary = "Add a new user to a workspace <=> Add a new user_workspace")
    @PostMapping(path = "/add")
    @RequireWorkspaceCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> add (@RequestParam(name = "user_id") int userId,
                                  @RequestParam(name = "workspace_id")int workspaceId,
                                  @RequestParam(name = "role") String roleName,
                                  HttpServletRequest request)
    {
        if(userWorkspaceService.findByUserAndWorkspace(userId,workspaceId).isPresent())
            return ResponseEntity.status(304).body(new MessageResponse("User is exsiting in workspace",304)); // existed a user workspace
        UserWorkspace userWorkspace = new UserWorkspace();
        String roleNameSta = "WS_"+roleName;
        Optional<Role> roleOptional = roleService.findByRoleName(roleNameSta);
        if(roleOptional.isPresent()) userWorkspace.setRole(roleOptional.get());
        else return ResponseEntity.status(204).body(new MessageResponse("Role not found",204));
        return userService.findByUserId(userId).map(user -> {
            userWorkspace.setUser(user);
            return workspaceService.findByWorkspaceId(workspaceId).map(workspace -> {
                userWorkspace.setWorkspace(workspace);
                Optional<UserWorkspace> addedUserWorkspace = userWorkspaceService.add(userWorkspace);
                return addedUserWorkspace.isPresent()
                        ?ResponseEntity.status(200).body(new MessageResponse("Add user work space successfully",200))
                        :ResponseEntity.status(304).body(new MessageResponse("Add user workspace fail",304));
            }).orElse(ResponseEntity.status(204).body(new MessageResponse("Workspace not found",204)));
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("User not found",204)));
    }


    @PutMapping(path = "/update")
    @RequireWorkspaceCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> update(@RequestBody UserWorkspaceDTO userWorkspaceDTO,
                                    HttpServletRequest request)
    {
           return ResponseEntity.ok("");
    }

    @Operation(summary = "Delete after a workspace was deleted")
    @DeleteMapping(path = "/delete-by-workspace")
    @RequireWorkspaceCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> deleteByWorkspace(@RequestParam(name = "workspace_id") int workspaceId,
                                               HttpServletRequest request)
    {
        return userWorkspaceService.deleteByWorkSpace(workspaceId)
                ?ResponseEntity.status(200).body(new MessageResponse("delete UserWorkspace by Workspace successfully",200))
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new MessageResponse("delete UserWorkspace by Workspace fail",304));
    }


    @Operation(summary = "Remove a user from a workspace")
    @DeleteMapping(path = "/remove-user-from-workspace")
    @RequireWorkspaceCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> removeUserFromWorkspace(@RequestParam(name = "user_id") int userId,
                                                     @RequestParam(name = "workspace_id") int workspaceId,
                                                     HttpServletRequest request)
    {
        if(userWorkspaceService.checkRole(workspaceId,userId,"WS_CREATOR")==true)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        return userWorkspaceService.removeUserFromWorkspace(userId,workspaceId)
                ?ResponseEntity.status(200).body(new MessageResponse("remove user from workspace successfully",200))
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new MessageResponse("remove user from workspace fail",304));
    }
}
