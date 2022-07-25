package com.example.projecti_trello_app_backend.repositories.column;

import com.example.projecti_trello_app_backend.entities.column.Columns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnRepo  extends JpaRepository<Columns,Integer> {

    @Query(value = "from Columns column where column.board.boardId =?1 and column.deleted =false " +
            "order by column.position asc")
    List<Columns> findAllByBoard(int boardId);

    @Query( value = "from Columns column where column.columnId=?1 and column.deleted=false")
    Optional<Columns> findByColumnId(int columnId);

    @Query(value = "select count (col) from Columns  col " +
            "where col.board.boardId =?1 and col.deleted =false")
    int getMaxCurrentPosition(int boardId);

    @Modifying
    @Transactional
    @Query(value = " update Columns column set column.deleted =true " +
            "where column.columnId =?1 and column.deleted = false")
    int delete(int columnId);
}
