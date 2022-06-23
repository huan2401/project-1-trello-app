package com.example.projecti_trello_app_backend.services.task;

import com.example.projecti_trello_app_backend.dto.TaskDTO;
import com.example.projecti_trello_app_backend.entities.task.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TaskService {

    Optional<Task> findByTaskId(int taskId);

    Optional<?> add (Task task, int columnId);

    Optional<Task> update(TaskDTO taskDTO);

    boolean delete(int taskId);

}
