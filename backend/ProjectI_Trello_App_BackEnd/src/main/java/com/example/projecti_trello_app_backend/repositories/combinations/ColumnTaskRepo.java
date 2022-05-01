package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColumnTaskRepo extends JpaRepository<ColumnTask,Integer> {

    @Query(value = "from ColumnTask coltask where coltask.column.columnId=?1 and coltask.staged=true")
    List<ColumnTask> findAllByColumn(int columnId);

    @Query (value = "from ColumnTask coltask where coltask.id=?1 and coltask.staged=true")
    Optional<ColumnTask> findById(int id);

    @Query(value = "from ColumnTask coltask where coltask.column.columnId=?1 " +
            "and  coltask.task.taskId =?2 and coltask.staged = true")
    Optional<ColumnTask> findByColumnAndTask(int columnId, int taskId);


}
