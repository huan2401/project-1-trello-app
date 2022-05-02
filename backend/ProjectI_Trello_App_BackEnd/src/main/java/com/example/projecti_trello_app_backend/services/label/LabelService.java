package com.example.projecti_trello_app_backend.services.label;

import com.example.projecti_trello_app_backend.dto.LabelDTO;
import com.example.projecti_trello_app_backend.entities.label.Label;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LabelService {

     List<Label> findAll();

     Optional<Label> findByLabelId(int labelId);

     List<Label> findAllByTask(int taskId);

     Optional<Label> add(Label label); // thêm một label vào 1 task

     Optional<Label> update(LabelDTO labelDTO);

     boolean delete (int labelId);

     boolean deleteByTask(int taskId);

}
