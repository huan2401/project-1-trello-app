package com.example.projecti_trello_app_backend.entities.board;

import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
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
@Table(schema = "${database.name}", name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", unique = true, nullable = false)
    private int boardId;


    @Column(name = "board_title")
    private String boardTitle;

    @Column(name = "board_background")
    private String boardBackground;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "deleted")
    private boolean deleted ;

    @ManyToOne
    @JoinColumn(name = "work_space_id")
    private Workspace workspace;

}
