package com.example.projecti_trello_app_backend.repositories.workspace;

import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceRepo extends JpaRepository<Workspace,Integer> {

    @Query(value = "from Workspace wp where wp.deleted =false")
    List<Workspace> findAll();

    @Query(value = "from Workspace wp where wp.workspaceId=?1 and wp.deleted=false")
    Optional<Workspace> findByWorkspaceId(int workSpaceId);

    @Modifying
    @Transactional
    @Query(value = "update Workspace wp set wp.deleted =true where wp.workspaceId =?1 and wp.deleted=false")
    int deleteByWorkspaceId(int workspaceId);
}
