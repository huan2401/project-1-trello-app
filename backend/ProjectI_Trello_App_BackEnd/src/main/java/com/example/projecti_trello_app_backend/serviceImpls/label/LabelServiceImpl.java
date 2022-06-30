package com.example.projecti_trello_app_backend.serviceImpls.label;

import com.example.projecti_trello_app_backend.dto.LabelDTO;
import com.example.projecti_trello_app_backend.entities.label.Label;
import com.example.projecti_trello_app_backend.repositories.label.LabelRepo;
import com.example.projecti_trello_app_backend.repositories.task.TaskRepo;
import com.example.projecti_trello_app_backend.services.label.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private TaskRepo taskRepo;


    @Override
    public List<Label> findAll() {
        try {
            return labelRepo.findAll();
        } catch (Exception exp)
        {
            exp.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Label> findByLabelId(int labelId) {
        try{
            return labelRepo.findByLabelId(labelId);
        } catch (Exception  exp)
        {
            exp.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Label> findAllByTask(int taskId) {
        try {
            return labelRepo.findAllByTask(taskId);
        } catch (Exception exp){
            log.error("find all label in a task error",exp);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Label> add(Label label) {
        try
        {
            return Optional.ofNullable(labelRepo.save(label));
        } catch (Exception ex)
        {
            log.error("add label to task error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Label> update(LabelDTO labelDTO) {
        try{
            Label labelToUpdate = labelRepo.findByLabelId(labelDTO.getLabelId()).get();
            labelToUpdate.setLabelColor(labelDTO!=null? labelDTO.getLabelColor():labelToUpdate.getLabelColor());
            labelToUpdate.setLabelTitle(labelDTO.getLabelTitle()!=null? labelDTO.getLabelTitle():labelToUpdate.getLabelTitle());
            return Optional.ofNullable(labelRepo.save(labelToUpdate));
        } catch (Exception ex)
        {
            log.error("update label error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(int labelId) {
        try{
            return labelRepo.delete(labelId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("deleted label by label id error",ex);

            return false;
        }
    }

    @Override
    public boolean deleteByTask(int taskId) {
        try{
            return labelRepo.deleteByTask(taskId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete label by task error",ex);
            return false;
        }
    }
}
