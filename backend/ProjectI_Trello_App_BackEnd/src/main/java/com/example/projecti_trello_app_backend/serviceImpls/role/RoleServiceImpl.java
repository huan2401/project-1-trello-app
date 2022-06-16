package com.example.projecti_trello_app_backend.serviceImpls.role;

import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.repositories.role.RoleRepo;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Optional<Role> findByRoleId(int roleId) {
        try{
            return roleRepo.findByRoleId(roleId);
        } catch (Exception ex)
        {
            log.error("find by role id error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        try{
            return roleRepo.findByRoleName(roleName);
        } catch (Exception ex)
        {
            log.error("find by role name error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> save(Role role) {
        try{
            return Optional.ofNullable(roleRepo.save(role));
        } catch (Exception ex)
        {
            log.error("save role error");
            return Optional.empty();
        }

    }
}
