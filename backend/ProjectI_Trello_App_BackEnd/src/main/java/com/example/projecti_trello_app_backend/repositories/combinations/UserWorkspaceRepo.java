package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWorkspaceRepo extends JpaRepository<UserWorkspace,Integer> {

    @Query(value = "from UserWorkspace userWorkspace where userWorkspace.id=?1 and userWorkspace.deleted=false")
    Optional<UserWorkspace> findById(int id);

    @Query(value = "from UserWorkspace  uswp where uswp.user.userId=?1 and " +
                    "uswp.role.roleName<>'WS_GUESS'" +
                   " and uswp.deleted =false")
    List<UserWorkspace> findByUser(int userId);

    @Query(value = "from UserWorkspace uswp where uswp.workspace.workspaceId=?1 and " +
                   "uswp.deleted =false")
    List<UserWorkspace> findByWorkspace(int workspaceId);

    @Query(value = "from UserWorkspace uswp where uswp.user.userId=?1 and " +
                   " uswp.workspace.workspaceId=?2 and uswp.deleted =false")
    Optional<UserWorkspace> findByUserAndWorkspace(int userId, int workSpaceId);

    @Query(value = "select count(uswp) from UserWorkspace uswp where uswp.workspace.workspaceId=?1 " +
            "and uswp.user.userId=?2 and uswp.deleted=false")
    int checkRole(int workspaceId, int userId,String roleName);

    @Query(value = "from UserWorkspace  uswp where uswp.user.userId =?1" +
            " and uswp.workspace.workspaceId=?2 " +
            "and uswp.role.roleName<> 'WS_GUESS' and uswp.deleted =false")
    boolean existsByUserAndWorkspace(int userId, int workSpaceId);

    @Modifying
    @Transactional
    @Query(value = "update UserWorkspace uswp set uswp.deleted =true where uswp.id =?1")
    int delete(int id);

    @Modifying
    @Transactional
    @Query(value = "update UserWorkspace uswp set uswp.deleted =true where uswp.workspace.workspaceId=?1" +
                   " and uswp.deleted=false")
    int deleteByWorkspace(int workspaceId);

    @Modifying
    @Transactional
    @Query(value = "update UserWorkspace uswp set uswp.deleted =true where uswp.user.userId=?1 and" +
            " uswp.workspace.workspaceId =?2 and uswp.deleted=false")
    int removeUserFromWorkspace(int userId, int workspaceId); // remove a user from a workspace
}
