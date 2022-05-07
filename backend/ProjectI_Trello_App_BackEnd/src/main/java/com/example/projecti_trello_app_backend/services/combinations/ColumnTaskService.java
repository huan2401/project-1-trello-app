package com.example.projecti_trello_app_backend.services.combinations;

import com.example.projecti_trello_app_backend.dto.ColumnTaskDTO;
import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import com.example.projecti_trello_app_backend.repositories.combinations.ColumnTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ColumnTaskService {

    List<ColumnTask> findAllByColumn (int columnId);

    List<ColumnTask> findAllByTask(int taskId);

    Optional<ColumnTask> findById (int id);

    Optional<ColumnTask> findByColumnAndTask(int columnId, int taskId);

    Optional<ColumnTask> add (ColumnTask columnTask);

    Optional<ColumnTask> update(int columnTaskId);

    boolean deleteByTask(int taskId);

    boolean deleteByColumn(int columnId);


}
