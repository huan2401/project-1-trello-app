package com.example.projecti_trello_app_backend.controllers.label;

import com.example.projecti_trello_app_backend.dto.LabelDTO;
import com.example.projecti_trello_app_backend.entities.label.Label;
import com.example.projecti_trello_app_backend.services.label.LabelService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/project1/api/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(labelService.findAll());
    }

    @GetMapping("/find-by-task")
    public ResponseEntity<?> findAllByTask(@RequestParam(name = "task_id") int taskId)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            return ResponseEntity.ok(labelService.findAllByTask(taskId));
        }).orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(name = "task_id") int taskId, @RequestBody Label label)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            label.setTask(task);
            return ResponseEntity.ok(labelService.add(label));
        }).orElse(ResponseEntity.ok(Optional.empty()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody LabelDTO labelDTO,
                                    @RequestParam(name="label_id") int labelId)
    {
        return labelService.findByLabelId(labelId).map(label -> {
            labelDTO.setLabelId(labelId);
            return ResponseEntity.ok(labelService.update(labelDTO));
        }).orElse(ResponseEntity.ok(Optional.empty()));
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<?> findByLabelId(@RequestParam(name = "id") int labelId){
         return ResponseEntity.ok(labelService.findByLabelId(labelId));
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> deleteById(@RequestParam(name ="label_id")int labelId)
    {
        return labelService.delete(labelId)?ResponseEntity.status(200).build()
                                            :ResponseEntity.status(304).build();
    }

    @DeleteMapping("/delete-by-task")
    public ResponseEntity<?> deleteByTask(@RequestParam(name = "task_id")int taskId)
    {
        return labelService.deleteByTask(taskId)?ResponseEntity.status(200).build()
                :ResponseEntity.status(304).build();
    }
}
