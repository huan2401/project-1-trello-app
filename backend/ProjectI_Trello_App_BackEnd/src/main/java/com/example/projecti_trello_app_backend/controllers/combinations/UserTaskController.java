package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserTask;
import com.example.projecti_trello_app_backend.services.combinations.UserTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("project1/api/user_task")
public class UserTaskController {

    @Autowired
     private UserTaskService userTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/find-by-task")
    public ResponseEntity<?> findAllByTask(@RequestParam(name = "task_id")int taskId)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            return ResponseEntity.ok(userTaskService.findByTask(taskId));
        }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/add")
    public ResponseEntity<?> add(@RequestParam(name = "user_id")int userId,
                                  @RequestParam(name = "task_id")int taskId)
    {
        UserTask userTaskToAdd = new UserTask();
        return taskService.findByTaskId(taskId).map(task -> {
            userTaskToAdd.setTask(task);
            return userService.findByUserId(userId).map(user -> {
                userTaskToAdd.setUser(user);
                return ResponseEntity.ok(userTaskService.add(userTaskToAdd));
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    @DeleteMapping("/delete-user-from-task")
    public ResponseEntity<?> deleteUserFromTask (@RequestParam(name = "task_id")int taskId,
                                           @RequestParam(name= "user_id") int userId)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            return userService.findByUserId(userId).map(user -> {
                return userTaskService.deleteUserFromTask(taskId,userId)?ResponseEntity.ok(userTaskService.findByTask(taskId))
                                                                        :ResponseEntity.status(304).build();
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }
}
