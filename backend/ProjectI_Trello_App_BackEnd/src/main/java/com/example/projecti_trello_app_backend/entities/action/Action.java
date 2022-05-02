package com.example.projecti_trello_app_backend.entities.action;

import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id", unique = true, nullable = false)
    private int actionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull
    private Task task;

    @Column(name = "action_content")
    private String actionContent;

    @Column(name = "processAt")
    private Timestamp processAt;
}
