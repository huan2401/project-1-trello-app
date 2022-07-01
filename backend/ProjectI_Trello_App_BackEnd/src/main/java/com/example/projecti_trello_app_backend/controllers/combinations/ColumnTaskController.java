package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/column_task")
public class ColumnTaskController {

    @Autowired
    private ColumnTaskService columnTaskService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/find-all-by-column")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findAllByColumn(@RequestParam(name = "column_id") int columId,
                                             HttpServletRequest request)
    {
        return ResponseEntity.ok(columnTaskService.findAllByColumn(columId));
    }

    @GetMapping("/find-by-column-and-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByComlumnAndTask(@RequestParam(name = "column_id")int columnId,
                                                  @RequestParam(name = "task_id") int taskId,
                                                  HttpServletRequest request)
    {
        return ResponseEntity.ok(columnTaskService.findByColumnAndTask(columnId,taskId));
    }

    @GetMapping("/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> add(@RequestParam(name = "column_id")int columnId,
                                 @RequestParam(name = "task_id") int taskId,
                                 HttpServletRequest request)
    {
        ColumnTask columnTask = new ColumnTask();
        columnTask.setStage(true);
        return columnService.findByColumnId(columnId).map(column->{
                columnTask.setColumn(column);
                return taskService.findByTaskId(taskId).map(task -> {
                    columnTask.setTask(task);
                    return ResponseEntity.ok(columnTaskService.add(columnTask));
                }).orElse(ResponseEntity.ok(Optional.empty()));
        }).orElse(ResponseEntity.ok(Optional.empty()));
    }


    @GetMapping("/change-stage")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> changeStage(@RequestParam(name = "start_col_id") int startColumnId,
                                         @RequestParam(name ="end_col_id") int endColumnId,
                                         @RequestParam(name = "task_id") int taskId,
                                         HttpServletRequest request)
    {
        Optional<ColumnTask> columnTask1 = columnTaskService.findByColumnAndTask(startColumnId,taskId);
        if(columnTask1.isPresent()) columnTaskService.update(columnTask1.get().getId());
        else return ResponseEntity.status(304).build();
        Optional<ColumnTask> columnTask2 = columnTaskService.findByColumnAndTask(endColumnId,taskId);
        if(columnTask2.isPresent()) {
             return columnTaskService.update(columnTask2.get().getId()).isPresent()
                     ?ResponseEntity.status(200).build():ResponseEntity.status(304).build();
        }
        else{
            ColumnTask columnTask = ColumnTask.builder().column(columnService.findByColumnId(endColumnId).get())
                                                        .task(taskService.findByTaskId(taskId).get())
                                                        .stage(true).build();
          return columnTaskService.add(columnTask).isPresent()?ResponseEntity.status(200).build()
                                                                : ResponseEntity.status(304).build();
        }
    }


}
