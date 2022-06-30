package com.example.projecti_trello_app_backend.controllers.task;

import com.example.projecti_trello_app_backend.dto.TaskDTO;
import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ColumnService columnService;

    @GetMapping("/find-by-task-id")
    public ResponseEntity<?> findByTaskId(@RequestParam(name = "task_id") int taskId,
                                          HttpServletRequest request)
    {
        return ResponseEntity.ok(taskService.findByTaskId(taskId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Task task, @RequestParam(name = "column_id")int columnId,
                                 HttpServletRequest request){
        return columnService.findByColumnId(columnId).map(column -> {
            return ResponseEntity.ok(taskService.add(task,column.getColumnId()));
        }).orElse(ResponseEntity.ok(Optional.empty()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody TaskDTO taskDTO,
                                    @RequestParam(name = "task_id") int taskId,
                                    HttpServletRequest request)
    {
        if(!taskService.findByTaskId(taskId).isPresent()) return ResponseEntity.ok(Optional.empty());
        taskDTO.setTaskId(taskId);
        return ResponseEntity.ok(taskService.update(taskDTO));
    }
}
