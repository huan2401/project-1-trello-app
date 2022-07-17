package com.example.projecti_trello_app_backend.entities.combinations;

import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "user_task")
public class UserTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id",unique = true, nullable = false )
    private int id;

    @Column(name = "task_detail")
    private String taskDetail;

    @Column(name ="assign-at")
    private Date assignedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull
    private Task task;

    @Column(name = "deleted")
    private boolean deleted;


}
