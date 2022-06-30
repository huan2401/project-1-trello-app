package com.example.projecti_trello_app_backend.repositories.label;

import com.example.projecti_trello_app_backend.entities.label.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepo extends JpaRepository<Label, Integer>{

    @Query(value = "from Label label where label.deleted = false")
     List<Label> findAll();

    @Query(value = "from Label label where label.labelId =?1 and label.deleted =false")
     Optional<Label> findByLabelId(Integer id);

    @Query(value = "from Label label where label.task.taskId =?1 and label.deleted =false")
     List<Label> findAllByTask(int taskId);

    @Modifying
    @Transactional
    @Query(value = "update Label label set label.deleted =true where label.labelId =?1")
     int delete (int labelId);

    @Modifying
    @Transactional
    @Query(value = " update Label label set label.deleted =true" +
            " where label.task.taskId =?1 and label.deleted=false")
    int deleteByTask(int taskId);

}
