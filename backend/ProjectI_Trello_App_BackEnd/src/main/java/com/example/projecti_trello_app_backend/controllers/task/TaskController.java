package com.example.projecti_trello_app_backend.controllers.task;

import com.example.projecti_trello_app_backend.dto.TaskDTO;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("project1/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/find-by-task-id")
    public ResponseEntity<?> findByTaskId(@RequestParam(name = "task_id") int taskId)
    {
        return ResponseEntity.ok(taskService.findByTaskId(taskId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody TaskDTO taskDTO,
                                    @RequestParam(name = "task_id") int taskId)
    {
        if(!taskService.findByTaskId(taskId).isPresent()) return ResponseEntity.ok(Optional.empty());
        taskDTO.setTaskId(taskId);
        return ResponseEntity.ok(taskService.update(taskDTO));
    }
}
