package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.UserTaskDTO;
import com.example.projecti_trello_app_backend.entities.combinations.UserTask;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Tag(name = "UserTask Controller",
description = "Use to represent the relationship between users and tasks")
@RestController
@RequestMapping("project1/api/user-task")
public class UserTaskController {

    @Autowired
     private UserTaskService userTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Find all user_task_s by task's id")
    @GetMapping("/find-by-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findAllByTask(@RequestParam(name = "task_id")int taskId,
                                           HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            return ResponseEntity.ok(userTaskService.findByTask(taskId));
        }).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Add a new user to a task <=> Add a new user_task")
    @GetMapping("/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> add(@RequestParam(name = "user_id")int userId,
                                 @RequestParam(name = "task_id")int taskId,
                                 HttpServletRequest request)
    {
        UserTask userTaskToAdd = new UserTask();
        return taskService.findByTaskId(taskId).map(task -> {
            userTaskToAdd.setTask(task);
            return userService.findByUserId(userId).map(user -> {
                userTaskToAdd.setUser(user);
                return ResponseEntity.ok(userTaskService.add(userTaskToAdd));
            }).orElse(ResponseEntity.status(204).build());
        }).orElse(ResponseEntity.status(204).build());
    }

    @Operation(summary = "Update user_task's infomation")
    @PutMapping(path = "/update")
    @SecurityRequirement(name="methodBearerAuth")
    public synchronized ResponseEntity<?> update(@RequestBody UserTaskDTO userTaskDTO,
                                    @RequestParam(name = "id")int id,
                                    HttpServletRequest request)
    {
        if(!userTaskService.findById(id).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("User Task not found",204));
        userTaskDTO.setId(id);
        Optional<UserTask> userTaskOptional = userTaskService.update(userTaskDTO);
        return userTaskOptional.isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Update UserTask successfully",200))
                :ResponseEntity.status(304).body(new MessageResponse("Update UserTask fail",304));
    }

    @Operation(summary = "Remove a user from a task")
    @DeleteMapping("/delete-user-from-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> deleteUserFromTask (@RequestParam(name = "task_id")int taskId,
                                                 @RequestParam(name= "user_id") int userId,
                                                 HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            return userService.findByUserId(userId).map(user -> {
                return userTaskService.deleteUserFromTask(taskId,userId)?ResponseEntity.ok(userTaskService.findByTask(taskId))
                                                                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }
}
