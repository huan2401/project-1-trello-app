package com.example.projecti_trello_app_backend.repositories.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserTaskRepo extends JpaRepository<UserTask,Integer> {

    @Query(value = "from UserTask userTask where userTask.task.taskId=?1 and userTask.deleted=false")
    public List<UserTask> findByTask(int taskId);

    @Query(value = "from UserTask userTask where userTask.user.userId =?1 and userTask.deleted =false")
    public List<UserTask> findByUser(int userId);

    @Modifying
    @Transactional
    @Query (value =" update UserTask userTask set userTask.deleted =true " +
            " where userTask.task.taskId =?1 and userTask.user.userId =?2 and userTask.deleted=false")
    public int deleteByUser (int taskId, int userId); // unassign a user from a task

    @Modifying
    @Transactional
    @Query(value = "update UserTask userTask set usertask.deleted=true where userTask.task.taskId=?1" +
            " and userTask.deleted=false ")
    public int deleteByTask(int taskId); // delete all when the task was deleted





}
