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

    @Query(value = "from ColumnTask coltask where coltask.column.columnId=?1 and coltask.stage=true " +
            "order by coltask.position asc")
    List<ColumnTask> findAllByColumn(int columnId);

    @Query(value ="from ColumnTask coltask where coltask.task.taskId = ?1 and coltask.stage =true")
    List<ColumnTask> findAllByTask(int taskId);

    @Query (value = "from ColumnTask coltask where coltask.id=?1 and coltask.stage=true")
    Optional<ColumnTask> findById(int id);

    @Query(value = "from ColumnTask coltask where coltask.column.columnId=?1 " +
            "and  coltask.task.taskId =?2 and coltask.stage = true")
    Optional<ColumnTask> findByColumnAndTask(int columnId, int taskId);

    @Query(value = "select count(coltask) from ColumnTask  coltask " +
            "where coltask.column.columnId =?1 and coltask.stage=true")
    int getMaxCurrentPosition(int columnId);

    @Query(value = "from ColumnTask coltask where coltask.column.columnId =?1 and " +
            " coltask.position >= ?2 and coltask.stage=true")
    List<ColumnTask> getByAfterAPosition(int columnId,int position);

    @Modifying
    @Transactional
    @Query(value = "update ColumnTask  coltask set coltask.stage =false" +
            " where coltask.task.taskId=?1 and coltask.stage =true")
    int deleteByTask(int taskId);

    @Modifying
    @Transactional
    @Query(value = "update ColumnTask coltask set coltask.stage=false" +
            " where coltask.column.columnId=?1 and coltask.stage =true")
    int deleteByColumn(int columnId);

}
