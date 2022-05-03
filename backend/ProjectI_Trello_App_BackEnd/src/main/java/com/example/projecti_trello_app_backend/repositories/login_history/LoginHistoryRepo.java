package com.example.projecti_trello_app_backend.repositories.login_history;

import com.example.projecti_trello_app_backend.entities.login_history.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface LoginHistoryRepo extends JpaRepository<LoginHistory,Integer> {
}
