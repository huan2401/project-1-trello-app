package com.example.projecti_trello_app_backend.controllers.action;

import com.example.projecti_trello_app_backend.entities.action.Action;
import com.example.projecti_trello_app_backend.services.action.ActionService;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/find-by-id")
    public ResponseEntity<?> findByActionId(@RequestParam(name = "action_id") int actionId)
    {
        return actionService.findByActionId(actionId).isPresent()
                ?ResponseEntity.ok(actionService.findByActionId(actionId))
                :ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/find-by-task")
    public ResponseEntity<?> findByTask(@RequestParam(name = "task_id")int taskId)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            List<Action> actions = actionService.findByTask(taskId);
            return actions.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok(actions);
        }).orElse(ResponseEntity.noContent().build());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addAction(@RequestBody Action action,
                                       @RequestParam(name = "user_id")int userId,
                                       @RequestParam(name = "task_id") int taskId)
    {
        return userService.findByUserId(userId).map(user -> {
            action.setUser(user);
            return taskService.findByTaskId(taskId).map(task -> {
                action.setTask(task);
                Optional<Action> actionOptional = actionService.add(action);
                return actionOptional.isPresent()?ResponseEntity.ok(actionOptional):ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.noContent().build());
        }).orElse(ResponseEntity.noContent().build());
    }
}
