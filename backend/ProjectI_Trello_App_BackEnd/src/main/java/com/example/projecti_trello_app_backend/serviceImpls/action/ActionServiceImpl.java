package com.example.projecti_trello_app_backend.serviceImpls.action;

import com.example.projecti_trello_app_backend.entities.action.Action;
import com.example.projecti_trello_app_backend.repositories.action.ActionRepo;
import com.example.projecti_trello_app_backend.services.action.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionRepo actionRepo;

    @Override
    public Optional<Action> findByActionId(int actionId) {
       try{
           return actionRepo.findByActionId(actionId);
       } catch (Exception ex)
       {
           log.error("find action by id error",ex);
           return Optional.empty();
       }
    }

    @Override
    public List<Action> findByTask(int taskId) {
        try{
            return actionRepo.findAllByTask(taskId);
        } catch (Exception ex)
        {
            log.error("find actions by task error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Action> add(Action action) {
        try{
            return Optional.ofNullable(actionRepo.save(action));
        } catch (Exception ex)
        {
            log.error("add action error ",ex);
            return Optional.empty();
        }
    }
}
