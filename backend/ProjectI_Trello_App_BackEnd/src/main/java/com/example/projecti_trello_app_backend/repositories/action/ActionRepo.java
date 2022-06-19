package com.example.projecti_trello_app_backend.repositories.action;

import com.example.projecti_trello_app_backend.entities.action.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepo extends JpaRepository<Action,Integer> {

    Optional<Action> findByActionId(int actionId);

    @Query(value = "from Action action where action.task.taskId=?1 order by action.processAt desc")
     List<Action> findAllByTask(int taskId);
}
