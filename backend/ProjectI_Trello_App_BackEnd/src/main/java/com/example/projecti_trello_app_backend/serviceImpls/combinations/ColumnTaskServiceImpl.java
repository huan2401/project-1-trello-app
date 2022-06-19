package com.example.projecti_trello_app_backend.serviceImpls.combinations;

import com.example.projecti_trello_app_backend.dto.ColumnTaskDTO;
import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import com.example.projecti_trello_app_backend.repositories.column.ColumnRepo;
import com.example.projecti_trello_app_backend.repositories.combinations.ColumnTaskRepo;
import com.example.projecti_trello_app_backend.repositories.task.TaskRepo;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.ExportException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ColumnTaskServiceImpl implements ColumnTaskService {

    @Autowired
    private ColumnTaskRepo columnTaskRepo;

    @Autowired
    private ColumnRepo columnRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public List<ColumnTask> findAllByColumn(int columnId) {
        try{
            if(!columnRepo.findByColumnId(columnId).isPresent())
            {
                log.warn("column is nt existed");
                return Collections.emptyList();
            }
            return columnTaskRepo.findAllByColumn(columnId);
        } catch (Exception ex)
        {
            log.error("find column_task by column error", ex);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ColumnTask> findAllByTask(int taskId) {
        try{
            if(!taskRepo.findByTaskId(taskId).isPresent())
            {
                log.warn("task is nt existed");
                return Collections.emptyList();
            }
            return columnTaskRepo.findAllByTask(taskId);
        } catch (Exception ex)
        {
            log.error("find column_task by column error", ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ColumnTask> findById(int id) {
        try
        {
            return columnTaskRepo.findById(id);
        } catch (Exception ex)
        {
            log.error("find column_task by id error", ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ColumnTask> findByColumnAndTask(int columnId, int taskId) {
        try {
            return columnRepo.findByColumnId(columnId).map(columns -> {
                 return taskRepo.findByTaskId(taskId).map(task -> {
                     return columnTaskRepo.findByColumnAndTask(columnId,taskId);
                 }).orElse(Optional.empty());
            }).orElse(Optional.empty());
        } catch (Exception ex)
        {
            log.error("find column_task by both column and task error",ex );
            return Optional.empty();
        }
    }

    @Override
    public Optional<ColumnTask> add(ColumnTask columnTask) {
        try {
            return Optional.ofNullable(columnTaskRepo.save(columnTask));
        } catch (Exception ex)
        {
            log.error("add column_task error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<ColumnTask> update(int columnTaskId) {
        try{
            if(!columnTaskRepo.findById(columnTaskId).isPresent()) return Optional.empty();
            ColumnTask columnTaskToUpdate = columnTaskRepo.findById(columnTaskId).get();
            if(columnTaskToUpdate.isStage()) columnTaskToUpdate.setStage(false);
            else columnTaskToUpdate.setStage(true);
            return Optional.ofNullable(columnTaskRepo.save(columnTaskToUpdate));
        } catch (Exception ex)
        {
            log.error("update column_task error", ex);
            return Optional.empty();
        }
    }


    @Override
    public Optional<ColumnTask> update(ColumnTaskDTO columnTaskDTO) {
        try{
            if(!columnTaskRepo.findByColumnAndTask(columnTaskDTO.getColumnId(),columnTaskDTO.getTaskId()).isPresent())
                return Optional.empty();
            ColumnTask columnTaskToUpdate = columnTaskRepo.findByColumnAndTask(
                                                            columnTaskDTO.getColumnId(),columnTaskDTO.getTaskId()).get();
            columnTaskToUpdate.setStage(columnTaskDTO.getStaged()!=null?columnTaskDTO.getStaged()
                                                                        :columnTaskToUpdate.isStage());
            return Optional.ofNullable(columnTaskRepo.save(columnTaskToUpdate));
        }catch (Exception ex)
        {
            log.error("update column task (DTO params) error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteByTask(int taskId) {
        try{
            return columnTaskRepo.deleteByTask(taskId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete column_task by task error",ex);
            return false;
        }
    }

    @Override
    public boolean deleteByColumn(int columnId) {
        try{
            for(var coltask: columnTaskRepo.findAllByColumn(columnId))
            {
                taskRepo.delete(coltask.getTask().getTaskId());
            }
            return columnTaskRepo.deleteByTask(columnId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete column_task by column error",ex);
            return false;
        }
    }
}
