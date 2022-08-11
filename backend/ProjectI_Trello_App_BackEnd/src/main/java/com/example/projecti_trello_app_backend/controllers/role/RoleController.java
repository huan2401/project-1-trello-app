package com.example.projecti_trello_app_backend.controllers.role;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name="Role Controller")
@RestController
@RequestMapping("project1/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Find a role by role's name")
    @GetMapping(path = "/find-by-role-name")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByRoleName(@RequestParam(name = "role_name")String roleName,
                                            HttpServletRequest request)
    {
        Optional<Role> roleOptional = roleService.findByRoleName(roleName);
        return roleOptional.isPresent()?ResponseEntity.ok(roleOptional)
                                        :ResponseEntity.status(204).body(new MessageResponse("Role not found",204));
    }

    @Operation(summary = "Find all roles of ta role's type")
    @GetMapping(path = "/find-by-role-type")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByRoleType (@RequestParam(name = "role_type")String roleType,
                                             HttpServletRequest request)
    {
        return roleService.findByRoleType(roleType).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("Not found any role of this type",204))
                :ResponseEntity.ok(roleService.findByRoleType(roleType));
    }

    @Operation(summary = "Add a new role")
    @PostMapping(path = "/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> add(@RequestBody Role role,
                                 HttpServletRequest request)
    {
        if(role.getRoleType().equals("Workspace"))
            role.setRoleName("WS_"+role.getRoleName().toUpperCase());
        else if(role.getRoleType().equals("Board"))
            role.setRoleName("BOARD_"+role.getRoleName());
        return roleService.add(role).isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Add role successfully",200))
                :ResponseEntity.status(304).body(new MessageResponse("Add role fail",304));
    }

}
