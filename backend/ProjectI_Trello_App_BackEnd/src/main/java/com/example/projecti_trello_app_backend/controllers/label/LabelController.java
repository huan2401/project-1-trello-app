package com.example.projecti_trello_app_backend.controllers.label;

import com.example.projecti_trello_app_backend.dto.LabelDTO;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.label.Label;
import com.example.projecti_trello_app_backend.services.label.LabelService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Tag(name = "Label Controller")
@RestController
@RequestMapping("/project1/api/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Find all labels")
    @GetMapping("/find-all")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(labelService.findAll());
    }

    @Operation(summary = "Find a label by id")
    @GetMapping("/find-by-id")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByLabelId(@RequestParam(name = "id") int labelId,
                                           HttpServletRequest request){
        return ResponseEntity.ok(labelService.findByLabelId(labelId));
    }

    @Operation(summary = "Find all labels of a task")
    @GetMapping("/find-by-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findAllByTask(@RequestParam(name = "task_id") int taskId,
                                           HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            return ResponseEntity.ok(labelService.findAllByTask(taskId));
        }).orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @Operation(summary = "Add a new label to a task")
    @PostMapping("/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> add(@RequestParam(name = "task_id") int taskId,
                                 @RequestBody Label label,
                                 HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            label.setTask(task);
            return ResponseEntity.ok(labelService.add(label));
        }).orElse(ResponseEntity.ok(Optional.empty()));
    }

    @Operation(summary = "Update a label's infomation")
    @PutMapping("/update")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> update(@RequestBody LabelDTO labelDTO,
                                    @RequestParam(name="label_id") int labelId,
                                    HttpServletRequest request)
    {
        return labelService.findByLabelId(labelId).map(label -> {
            labelDTO.setLabelId(labelId);
            return ResponseEntity.ok(labelService.update(labelDTO));
        }).orElse(ResponseEntity.ok(Optional.empty()));
    }

    @Operation(summary = "Delete a label")
    @DeleteMapping("/delete-by-id")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> deleteById(@RequestParam(name ="label_id")int labelId)
    {
        return  labelService.findByLabelId(labelId).isPresent() && labelService.delete(labelId)
                ?ResponseEntity.status(200).body(new MessageResponse("Delete label by id successfully",200))
                :ResponseEntity.status(204).body(new MessageResponse("Delete label fail",204));
    }

    @Operation(summary = "Delete all labels of a task when it was deleted")
    @DeleteMapping("/delete-by-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> deleteByTask(@RequestParam(name = "task_id")int taskId,
                                          HttpServletRequest request)
    {
        return  taskService.findByTaskId(taskId).isPresent()&&labelService.deleteByTask(taskId)
                ?ResponseEntity.status(200).body(new MessageResponse("Delete label by task successfully",200))
                :ResponseEntity.status(304).body(new MessageResponse("Delete label by task fail",304));
    }
}
