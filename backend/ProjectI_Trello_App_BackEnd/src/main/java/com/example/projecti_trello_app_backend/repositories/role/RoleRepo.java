package com.example.projecti_trello_app_backend.repositories.role;

import com.example.projecti_trello_app_backend.entities.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleId(int roleId);

    Optional<Role> findByRoleName(String roleName);

    @Modifying
    @Transactional
    @Query("update Role  role set role.deleted =true where role.roleId=?1 and role.deleted=false ")
    int delete(int roleId);
}
