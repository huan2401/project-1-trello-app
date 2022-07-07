package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTaskRepo extends JpaRepository<UserTask,Integer> {

    @Query(value = "from UserTask userTask where userTask.id =?1 and userTask.deleted=false")
    Optional<UserTask> findById(int id);

    @Query(value = "from UserTask userTask where userTask.task.taskId=?1 and userTask.deleted=false")
     List<UserTask> findByTask(int taskId);

    @Query(value = "from UserTask userTask where userTask.user.userId =?1 and userTask.deleted =false")
     List<UserTask> findByUser(int userId);

    @Query("from UserTask ustk where ustk.user.userId=?1 and ustk.task.taskId=?2 and ustk.deleted =false")
    Optional<UserTask> findByUserAndTask(int userId, int taskId);

    @Query(value = "select ustk from UserTask ustk where " +
            "ustk.user.userId =?1 and ustk.task.taskId =?2 and ustk.deleted =false")
    Boolean existsUserAndTask(int userId, int taskId);

    @Modifying
    @Transactional
    @Query (value =" update UserTask userTask set userTask.deleted =true " +
            " where userTask.task.taskId =?1 and userTask.user.userId =?2 and userTask.deleted=false")
    int deleteByUser (int taskId, int userId); // unassign a user from a task

    @Modifying
    @Transactional
    @Query(value = "update UserTask userTask set userTask.deleted=true where userTask.task.taskId=?1" +
            " and userTask.deleted=false ")
    int deleteByTask(int taskId); // delete all when the task was deleted

    @Modifying
    @Transactional
    @Query(value = "update UserTask userTask set userTask.deleted= true where userTask.id =?1 and userTask.deleted=false ")
     int deleteById(int id);





}
