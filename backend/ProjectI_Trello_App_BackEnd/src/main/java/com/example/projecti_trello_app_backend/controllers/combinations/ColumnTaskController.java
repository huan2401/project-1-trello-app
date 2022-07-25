package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.combinations.ColumnTask;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name = "ColumnTask Controller",
        description = "Use to represent  the relationship between columns and tasks")
@RestController
@RequestMapping("project1/api/column_task")
public class ColumnTaskController {

    @Autowired
    private ColumnTaskService columnTaskService;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Find all column_tasks by column's id")
    @GetMapping("/find-all-by-column")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findAllByColumn(@RequestParam(name = "column_id") int columId,
                                             HttpServletRequest request)
    {
        return ResponseEntity.ok(columnTaskService.findAllByColumn(columId));
    }

    @Operation(summary = "Find a column_task")
    @GetMapping("/find-by-column-and-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByComlumnAndTask(@RequestParam(name = "column_id")int columnId,
                                                  @RequestParam(name = "task_id") int taskId,
                                                  HttpServletRequest request)
    {
        return ResponseEntity.ok(columnTaskService.findByColumnAndTask(columnId,taskId));
    }

    @Operation(summary ="Add a task to a column <=> Add a new column_task ")
    @GetMapping("/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> add(@RequestParam(name = "column_id")int columnId,
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


    @Operation(summary = "Move a task from a column to another")
    @GetMapping("/move")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> move(@RequestParam(name = "start_col_id") int startColumnId,
                                                      @RequestParam(name ="end_col_id") int endColumnId,
                                                      @RequestParam(name = "new_position") int newPostion,
                                                      @RequestParam(name = "task_id") int taskId,
                                                      HttpServletRequest request)
    {
        Optional<ColumnTask> columnTask1 = columnTaskService.findByColumnAndTask(startColumnId,taskId);
        if(columnTask1.isPresent()) {
            ColumnTask columnTask = columnTask1.get();
            columnTask.setPosition(0);
            columnTaskService.changeStage(columnTask.getId());
            columnTaskService.changePosition(startColumnId,columnTask1.get().getPosition(),"remove");
        }
        else return ResponseEntity.status(304).build();
        Optional<ColumnTask> columnTask2 = columnTaskService.findByColumnAndTask(endColumnId,taskId);
        if(columnTask2.isPresent()) {
            return columnTaskService.changeStage(columnTask2.get().getId()).isPresent()
                    && columnTaskService.changePosition(endColumnId,newPostion,"move")
                    ?ResponseEntity.status(200).build():ResponseEntity.status(304).build();
        }
        else{
            ColumnTask columnTask = ColumnTask.builder().column(columnService.findByColumnId(endColumnId).get())
                    .task(taskService.findByTaskId(taskId).get())
                    .stage(true).build();
            return columnTaskService.add(columnTask).isPresent() && columnTaskService.changePosition(endColumnId,newPostion,"move")
                    ?ResponseEntity.status(200).build()
                    : ResponseEntity.status(304).build();
        }
    }

    @Operation(summary = "Update postion of tasks in a column when a task was moved or removed in a postion" +
            "in the column ")
    @GetMapping("/change-position")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> changePosition(@RequestParam(name = "column_id") int columnId,
                                                         @RequestParam(name = "added_postion") int position,
                                                         @RequestParam(name = "action") String action,
                                                         HttpServletRequest request)
    {
        if(!columnService.findByColumnId(columnId).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("Column not found"));
        return columnTaskService.changePosition(columnId,position,action)
                ?ResponseEntity.status(200).body(new MessageResponse("Change position successfully"))
                :ResponseEntity.status(304).body(new MessageResponse("Change position fail"));
    }
}
