package com.example.projecti_trello_app_backend.serviceImpls.task;

import com.example.projecti_trello_app_backend.dto.TaskDTO;
import com.example.projecti_trello_app_backend.entities.column.Columns;
import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.repositories.column.ColumnRepo;
import com.example.projecti_trello_app_backend.repositories.combinations.ColumnTaskRepo;
import com.example.projecti_trello_app_backend.repositories.combinations.UserTaskRepo;
import com.example.projecti_trello_app_backend.repositories.comment.CommentRepo;
import com.example.projecti_trello_app_backend.repositories.task.TaskRepo;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.comment.CommentService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private ColumnRepo columnRepo;

    @Autowired
    private ColumnTaskRepo columnTaskRepo;

    @Autowired
    private UserTaskRepo userTaskRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Override
    public Optional<Task> findByTaskId(int taskId) {
        try{
            return taskRepo.findByTaskId(taskId);
        } catch (Exception ex)
        {
            log.error("find task by task id error", ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Task> update(TaskDTO taskDTO) {
        try{
            if(!taskRepo.findByTaskId(taskDTO.getTaskId()).isPresent())
            {
                log.warn("Task is not existed");
                return Optional.empty();
            }
            Task taskToUpdate= taskRepo.findByTaskId(taskDTO.getTaskId()).get();
            taskToUpdate.setTaskName(taskDTO.getTaskName()!=null?taskDTO.getTaskName():taskToUpdate.getTaskName());
            taskToUpdate.setTaskDescription(taskDTO.getTaskDescription()!=null?taskDTO.getTaskDescription():taskToUpdate.getTaskDescription());
            taskToUpdate.setTaskBackgroundUrl(taskDTO.getTaskBackground()!=null?taskDTO.getTaskBackground():taskToUpdate.getTaskBackgroundUrl());
            taskToUpdate.setStartAt(taskDTO.getStartAt()!=null?taskDTO.getStartAt():taskToUpdate.getStartAt());
            taskToUpdate.setDueAt(taskDTO.getDueAt()!=null?taskDTO.getDueAt():taskToUpdate.getDueAt());
            taskToUpdate.setIsDone(taskDTO.getDone()!=null?taskDTO.getDone():taskToUpdate.getIsDone());
            taskToUpdate.setIsReviewed(taskDTO.getReviewed()!=null?taskDTO.getReviewed():taskToUpdate.getIsReviewed());
            return Optional.of(taskRepo.save(taskToUpdate));
        } catch (Exception ex)
        {
            log.error("update task error", ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<?> add(Task task, int columnId) {
        try
        {
            task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Task taskAdded = taskRepo.save(task);
            Columns column = columnRepo.findByColumnId(columnId).get();
            ColumnTask columnTask =ColumnTask.builder().column(column).task(taskAdded).stage(true).build();
            return Optional.ofNullable(columnTaskRepo.save(columnTask));
        } catch (Exception ex)
        {
            log.error("add task error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int taskId) {
        try {
            return taskRepo.delete(taskId)>0
                    && columnTaskRepo.deleteByTask(taskId)>0
                    && userTaskRepo.deleteByTask(taskId)>0
                    && commentRepo.deleteByTask(taskId) >0
                    ?true:false;
        } catch (Exception ex)
        {
            log.error("delete task error", ex);
            return false;
        }
    }
}
