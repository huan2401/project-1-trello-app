package com.example.projecti_trello_app_backend.services.combinations;

import com.example.projecti_trello_app_backend.dto.UserTaskDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserTask;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserTaskService {

    Optional<UserTask> findById(int id);

    List<UserTask> findByTask(int taskId);

    List<UserTask> findByUser(int userId);

    boolean existsUserTask(int userId, int taskId);

    Optional<UserTask> add(UserTask userTask);

    Optional<UserTask> update(UserTaskDTO userTaskDTO);

    boolean deleteUserFromTask(int taskId, int userId);

    boolean deleteByTask(int taskId);

    boolean deleteById(int id);
}
