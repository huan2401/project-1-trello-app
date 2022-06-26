package com.example.projecti_trello_app_backend.controllers.role;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("project1/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/find-by-role-name")
    public ResponseEntity<?> findByRoleName(@RequestParam(name = "role_name")String roleName)
    {
        Optional<Role> roleOptional = roleService.findByRoleName(roleName);
        return roleOptional.isPresent()?ResponseEntity.ok(roleOptional)
                                        :ResponseEntity.status(304).body(new MessageResponse("Role not found"));
    }

    @GetMapping(path = "/find-by-role-type")
    public ResponseEntity<?> findByRoleType (@RequestParam(name = "role_type")String roleType)
    {
        return roleService.findByRoleType(roleType).isEmpty()
                ?ResponseEntity.status(204).body(new MessageResponse("Not found any role of this type"))
                :ResponseEntity.ok(roleService.findByRoleType(roleType));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> add(@RequestBody Role role)
    {
        return roleService.add(role).isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Add role successfully"))
                :ResponseEntity.status(304).body(new MessageResponse("Add role fail"));
    }

}
