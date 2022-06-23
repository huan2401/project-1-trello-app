package com.example.projecti_trello_app_backend.entities.task;

import com.example.projecti_trello_app_backend.entities.column.Columns;
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
@Table(schema = "${database.name}", name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", unique = true)
    private int taskId;


    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_background")
    private String taskBackgroundUrl;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "due_at")
    private Timestamp dueAt;

    @Column(name = "reviewed")
    private Boolean isReviewed;

    @Column(name = "done")
    private Boolean isDone;

    @Column(name = "deleted")
    private boolean isDeleted;

}
