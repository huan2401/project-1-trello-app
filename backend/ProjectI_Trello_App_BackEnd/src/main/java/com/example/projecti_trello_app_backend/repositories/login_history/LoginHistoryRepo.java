package com.example.projecti_trello_app_backend.repositories.login_history;

import com.example.projecti_trello_app_backend.entities.login.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepo extends JpaRepository<LoginHistory,Integer> {
}
