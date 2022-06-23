package com.example.projecti_trello_app_backend.services.role;

import com.example.projecti_trello_app_backend.entities.role.Role;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;
import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public interface RoleService {

    Optional<Role> findByRoleId (int roleId);

    Optional<Role> findByRoleName(String roleName);

    Optional<Role> save(Role role);
}
