package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBoardRoleRepo extends JpaRepository<UserBoardRole,Integer> {

    @Query(value = "from UserBoardRole usbr where usbr.deleted=false")
    List<UserBoardRole> findById(int id);

    @Query(value = "from UserBoardRole usbr where usbr.board.boardId=?1 and usbr.deleted=false")
    List<UserBoardRole> findByBoard(int boardId);

    @Query(value = "from UserBoardRole usbr where usbr.user.userId=?1 and usbr.deleted =false")
    List<UserBoardRole> findByUser(int userId);

    @Query(value = "from UserBoardRole usbr where " +
            "usbr.user.userId=?1 and usbr.board.boardId=?2 and usbr.deleted =false")
    Optional<UserBoardRole> findByUserAndBoard(int userId, int boardId);

    @Modifying
    @Transactional
    @Query(value = "update UserBoardRole usbr set usbr.role.roleName =?3 " +
            " where usbr.user.userId =?1 and usbr.board.boardId =?2 and usbr.deleted =false")
    int setRoleForUser(int userId, int boardId, String roleName);

    @Modifying
    @Transactional
    @Query(value = "update UserBoardRole usbr set usbr.deleted=true where " +
            " usbr.board.boardId=?1 and usbr.deleted =false ")
    int deleteByBoard(int boardId);

    @Modifying
    @Transactional
    @Query(value = "update UserBoardRole usbr set usbr.deleted=true where " +
            "usbr.user.userId =?1 and usbr.board.boardId =?1 and usbr.deleted=false")
    int deleteUserFromBoard(int userId, int boardId);

}
