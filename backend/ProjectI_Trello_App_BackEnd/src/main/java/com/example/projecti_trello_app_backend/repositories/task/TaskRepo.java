package com.example.projecti_trello_app_backend.repositories.task;

import com.example.projecti_trello_app_backend.entities.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {

    @Query (value = "from Task task where task.isDeleted =false")
     List<Task> findAll();


    @Query(value = "from Task task where task.taskId =?1 and task.isDeleted =false")
     Optional<Task> findByTaskId(int taskId);

    @Query (value ="from Task task where task.taskName like concat('%',?1,'%') and task.isDeleted =false")
     List<Task> findByTaskName(String taskName);

    @Modifying
    @Transactional
    @Query(value = "update Task task set task.isDeleted =true where task.taskId =?1 and task.isDeleted =false")
     int delete (int taskId);
}
