package com.example.projecti_trello_app_backend.serviceImpls.combinations;

import com.example.projecti_trello_app_backend.dto.UserTaskDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserTask;
import com.example.projecti_trello_app_backend.repositories.combinations.UserTaskRepo;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserTaskServiceImpl implements UserTaskService {

    @Autowired
    private UserTaskRepo userTaskRepo;

    @Autowired
    private  TaskService taskService;

    @Autowired
    private  UserService userService;

    @Override
    public List<UserTask> findByTask(int taskId) {
       try
       {
           return userTaskRepo.findByTask(taskId);
       } catch (Exception ex)
       {
           log.error("find user_task by task error",ex);
           return Collections.emptyList();
       }
    }

    @Override
    public List<UserTask> findByUser(int userId) {
        try
        {
            return userTaskRepo.findByUser(userId);
        } catch (Exception ex)
        {
            log.error("find user_task by user error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<UserTask> add(UserTask userTask) {
        try{
            userTask.setAssignedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(userTaskRepo.save(userTask));
        } catch (Exception ex){
            log.error("add user_task error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserTask> update(UserTaskDTO userTaskDTO) { // not have to
        return Optional.empty();
    }

    @Override
    public boolean deleteUserFromTask(int taskId, int userId) {
        try{
            return userTaskRepo.deleteByUser(taskId, userId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete user from task error",ex);
            return false;

        }
    }

    @Override
    public boolean deleteByTask(int taskId) {
        try{
            return userTaskRepo.deleteByTask(taskId)>0?true:false;
        }catch (Exception ex)
        {
            log.error("delete user_task by task error",ex);
            return false;
        }
    }
}
