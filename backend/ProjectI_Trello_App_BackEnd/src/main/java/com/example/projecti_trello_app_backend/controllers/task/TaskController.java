package com.example.projecti_trello_app_backend.controllers.task;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.dto.TaskDTO;
import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name = "Task Controller")
@RestController
@RequestMapping("project1/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ColumnService columnService;

    @Operation(summary = "Find a task by id")
    @GetMapping("/find-by-task-id")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByTaskId(@RequestParam(name = "task_id") int taskId,
                                          HttpServletRequest request)
    {
        return ResponseEntity.ok(taskService.findByTaskId(taskId));
    }

    @Operation(summary = "Add a new task to a column")
    @PostMapping("/add")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> add(@RequestBody Task task, @RequestParam(name = "column_id")int columnId,
                                 HttpServletRequest request){
        return columnService.findByColumnId(columnId).map(column -> {
            return taskService.add(task,columnId).isPresent()
                    ?ResponseEntity.status(200).body(new MessageResponse("Add column successfully",200))
                    :ResponseEntity.status(304).body(new MessageResponse("Add column fail",304));
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("Column not found",204)));
    }

    @Operation(summary = "Update task's infomation")
    @PutMapping("/update")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> update(@RequestBody TaskDTO taskDTO,
                                    @RequestParam(name = "task_id") int taskId,
                                    HttpServletRequest request)
    {
        if(!taskService.findByTaskId(taskId).isPresent()) return ResponseEntity.ok(Optional.empty());
        taskDTO.setTaskId(taskId);
        return ResponseEntity.ok(taskService.update(taskDTO));
    }
}
