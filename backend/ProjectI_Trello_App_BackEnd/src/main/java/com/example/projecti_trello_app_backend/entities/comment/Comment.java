package com.example.projecti_trello_app_backend.entities.comment;

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
@Table(schema = "${database.name}", name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private int commentId;

    @Column(name="content")
    private String content;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name="edited_at")
    private Timestamp editedAt;

    @Column(name = "edited")
    private boolean edited;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name="task_id", nullable = false)
    @NotNull
    private Task task;

    @Column(name = "deleted")
    private boolean deleted;


}
