package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserWorkspaceDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/find-by-user")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id") int userId,
                                        HttpServletRequest request)
    {
        return userWorkspaceService.findByUser(userId).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("This user doesn't belong to any board"))
                :ResponseEntity.ok(userWorkspaceService.findByWorkspace(userId));
    }

    @GetMapping(path = "/find-by-workspace")
    public ResponseEntity<?> findByWorkspace(@RequestParam(name = "workspace_id")int workspaceId,
                                             HttpServletRequest request)
    {
        return userWorkspaceService.findByWorkspace(workspaceId).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("This workspace doesn't has any member"))
                :ResponseEntity.ok(userWorkspaceService.findByWorkspace(workspaceId));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add (@RequestParam(name = "user_id") int userId,
                                  @RequestParam(name = "workspace_id")int workspaceId,
                                  @RequestParam(name = "role") String roleName,
                                  HttpServletRequest request)
    {
        if(userWorkspaceService.findByUserAndWorkspace(userId,workspaceId).isPresent())
            return ResponseEntity.status(304).body(new MessageResponse("User is exsiting in workspace")); // existed a user workspace
        UserWorkspace userWorkspace = new UserWorkspace();
        String roleNameSta = "WS_"+roleName;
        Optional<Role> roleOptional = roleService.findByRoleName(roleNameSta);
        if(roleOptional.isPresent()) userWorkspace.setRole(roleOptional.get());
        else return ResponseEntity.status(304).body(new MessageResponse("Role not found"));
        return userService.findByUserId(userId).map(user -> {
            userWorkspace.setUser(user);
            return workspaceService.findByWorkspaceId(workspaceId).map(workspace -> {
                userWorkspace.setWorkspace(workspace);
                Optional<UserWorkspace> addedUserWorkspace = userWorkspaceService.add(userWorkspace);
                return addedUserWorkspace.isPresent()
                        ?ResponseEntity.status(200).body(new MessageResponse("Add user work space successfully"))
                        :ResponseEntity.status(304).body(new MessageResponse("Add user workspace fail"));
            }).orElse(ResponseEntity.status(204).body(new MessageResponse("Workspace not found")));
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("User not found")));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody UserWorkspaceDTO userWorkspaceDTO,
                                    HttpServletRequest request)
    {
           return ResponseEntity.ok("");
    }

    @DeleteMapping(path = "/delete-by-workspace")
    public ResponseEntity<?> deleteByWorkspace(@RequestParam(name = "workspace_id") int workspaceId,
                                               HttpServletRequest request)
    {
        return userWorkspaceService.deleteByWorkSpace(workspaceId)
                ?ResponseEntity.status(200).body(new MessageResponse("delete UserWorkspace by Workspace successfully "))
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new MessageResponse("delete UserWorkspace by Workspace fail"));
    }


    @DeleteMapping(path = "/remove-user-from-workspace")
    public ResponseEntity<?> removeUserFromWorkspace(@RequestParam(name = "user_id") int userId,
                                                     @RequestParam(name = "workspace_id") int workspaceId,
                                                     HttpServletRequest request)
    {
        if(userWorkspaceService.checkRole(workspaceId,userId,"WS_CREATOR")==true)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        return userWorkspaceService.removeUserFromWorkspace(userId,workspaceId)
                ?ResponseEntity.status(200).body(new MessageResponse("remove user from workspace successfully"))
                :ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new MessageResponse("remove user from workspace fail "));
    }
}
