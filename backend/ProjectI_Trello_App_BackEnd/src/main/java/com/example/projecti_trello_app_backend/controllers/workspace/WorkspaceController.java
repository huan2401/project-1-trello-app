package com.example.projecti_trello_app_backend.controllers.workspace;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.WorkSpaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.security.authorization.RequireWorkspaceCreator;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/project1/api/workspace")
@SecurityRequirement(name = "bearerAuth")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserWorkspaceService userWorkspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityUtils util;

    @GetMapping("/find-by-workspace-id")
    public ResponseEntity<?> findWorkSpaceById(@RequestParam(name = "workspace_id")int workSpaceId,
                                               HttpServletRequest request)
    {
        return workspaceService.findByWorkspaceId(workSpaceId).isPresent()
                ?ResponseEntity.ok(workspaceService.findByWorkspaceId(workSpaceId))
                :ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Workspace workspace,
                                 HttpServletRequest request)
    {
        Optional<Workspace> userWorkspaceToAdd = workspaceService.add(workspace);
        if(!userWorkspaceToAdd.isPresent()) return ResponseEntity.noContent().build();
        UserWorkspace userWorkspace = new UserWorkspace();
        int creatorId = util.getUserFromRequest(request).getUserId();
        return userService.findByUserId(creatorId).map(user -> {
            userWorkspace.setUser(user);
            userWorkspace.setWorkspace(userWorkspaceToAdd.get());
            Optional<Role> roleOptional = roleService.findByRoleName("WS_CREATOR");
            if(roleOptional.isPresent())
            userWorkspace.setRole(roleOptional.get());
            else return ResponseEntity.status(304).body(new MessageResponse("Role not found"));
            return userWorkspaceService.add(userWorkspace).isPresent()
                    ?ResponseEntity.status(200).body(new MessageResponse("Add workspace successfully"))
                    :ResponseEntity.status(304).body(new MessageResponse("Add workspace fail"));
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("User not found")));

    }

    @PutMapping("/update")
    @RequireWorkspaceCreator
    public ResponseEntity<?> update(@RequestBody WorkSpaceDTO workSpaceDTO,
                                    @RequestParam(name = "workspace_id") int workSpaceId,
                                    HttpServletRequest request)
    {
        return workspaceService.findByWorkspaceId(workSpaceId).map(workspace ->
        {
           workSpaceDTO.setWorkspaceId(workSpaceId);
           return ResponseEntity.ok(workspaceService.update(workSpaceDTO));
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/delete")
    @RequireWorkspaceCreator
    public ResponseEntity<?> delete(@RequestParam(name = "workspace_id") int workSpaceId,
                                    HttpServletRequest request)
    {
        return workspaceService.delete(workSpaceId)
                ?ResponseEntity.status(200).build()
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
