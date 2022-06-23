package com.example.projecti_trello_app_backend.services.action;

import com.example.projecti_trello_app_backend.entities.action.Action;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ActionService {

    Optional<Action> findByActionId(int actionId);

    List<Action> findByTask(int taskId);

    Optional<Action> add(Action action);

}
