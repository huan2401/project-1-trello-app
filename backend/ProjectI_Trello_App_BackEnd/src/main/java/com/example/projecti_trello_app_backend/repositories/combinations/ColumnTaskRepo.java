package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnTaskRepo extends JpaRepository<ColumnTask,Integer> {

    @Query(value = "from ColumnTask coltask where coltask.column.columnId=?1 and coltask.staged=true")
    List<ColumnTask> findAllByColumn(int columnId);

    @Query(value ="from ColumnTask coltask where coltask.task.taskId = ?1 and coltask.staged =true")
    List<ColumnTask> findAllByTask(int taskId);

    @Query (value = "from ColumnTask coltask where coltask.id=?1 and coltask.staged=true")
    Optional<ColumnTask> findById(int id);

    @Query(value = "from ColumnTask coltask where coltask.column.columnId=?1 " +
            "and  coltask.task.taskId =?2 and coltask.staged = true")
    Optional<ColumnTask> findByColumnAndTask(int columnId, int taskId);

    @Modifying
    @Transactional
    @Query(value = "update ColumnTask  coltask set coltask.staged =false" +
            " where coltask.task.taskId=?1 and coltask.staged =true")
    int deleteByTask(int taskId);

    @Modifying
    @Transactional
    @Query(value = "update ColumnTask coltask set coltask.staged=false" +
            " where coltask.column.columnId=?1 and coltask.staged =true")
    int deleteByColumn(int columnId);

}
