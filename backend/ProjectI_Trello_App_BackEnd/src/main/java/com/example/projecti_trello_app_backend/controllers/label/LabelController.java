package com.example.projecti_trello_app_backend.controllers.label;

import com.example.projecti_trello_app_backend.services.label.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project1/api/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll()
    {
        return ResponseEntity.ok(labelService.findAll());
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<?> findByLabelId(@RequestParam(name = "id") int labelId){
         return ResponseEntity.ok(labelService.findByLabelId(labelId));
    }
}
