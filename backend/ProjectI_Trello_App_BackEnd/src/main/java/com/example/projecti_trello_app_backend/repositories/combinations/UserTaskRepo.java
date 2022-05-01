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
    @Query (value =" update UserTask usertask set usertask.deleted =true " +
            " where usertask.task.taskId =?1 and usertask.user.userId =?2 and usertask.deleted=false")
    public boolean delete (int taskId, int userId);




}
