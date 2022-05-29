package com.example.projecti_trello_app_backend.repositories.board;

import com.example.projecti_trello_app_backend.entities.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepo extends JpaRepository<Board,Integer> {

    @Query("from Board board where board.deleted =false")
    List<Board> findAll();

    @Query(value = "from Board board where board.boardId =?1 and board.deleted =false")
     Optional<Board> findByBoardId(int boardId);

    @Modifying
    @Transactional
    @Query(value = "update Board  board set board.deleted=true " +
            " where  board.boardId =?1 and board.deleted =false")
    int delete (int boardId);


}
