package com.example.projecti_trello_app_backend.controllers.action;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.action.Action;
import com.example.projecti_trello_app_backend.services.action.ActionService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Tag(name="Action Controller")
@RestController
@RequestMapping("project1/api/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Find actions by action's id")
    @GetMapping(path = "/find-by-id")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByActionId(@RequestParam(name = "action_id") int actionId,
                                            HttpServletRequest request)
    {
        return actionService.findByActionId(actionId).isPresent()
                ?ResponseEntity.ok(actionService.findByActionId(actionId))
                :ResponseEntity.status(204).body(new MessageResponse("Action not found"));
    }

    @Operation(summary = "Find all actions of a task")
    @GetMapping(path = "/find-by-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByTask(@RequestParam(name = "task_id")int taskId,
                                        HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            List<Action> actions = actionService.findByTask(taskId);
            return actions.isEmpty()?ResponseEntity.status(204).body(new MessageResponse("This task doesn't has any action"))
                                    :ResponseEntity.ok(actions);
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("Task not found")));
    }

    @Operation(summary = "Add a action to a task")
    @PostMapping(path = "/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> addAction(@RequestBody Action action,
                                       @RequestParam(name = "task_id") int taskId,
                                       HttpServletRequest request)
    {
        int userId = util.getUserFromRequest(request).getUserId();
        return userService.findByUserId(userId).map(user -> {
            action.setUser(user);
            return taskService.findByTaskId(taskId).map(task -> {
                action.setTask(task);
                Optional<Action> actionOptional = actionService.add(action);
                return actionOptional.isPresent()?ResponseEntity.ok(actionOptional)
                                                :ResponseEntity.status(304).body(new MessageResponse("Add action fail"));
            }).orElse(ResponseEntity.status(204).body(new MessageResponse("Task not found")));
        }).orElse(ResponseEntity.noContent().build());
    }
}
