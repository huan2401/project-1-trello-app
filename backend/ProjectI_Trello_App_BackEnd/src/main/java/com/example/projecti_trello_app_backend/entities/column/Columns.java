package com.example.projecti_trello_app_backend.entities.column;

import com.example.projecti_trello_app_backend.entities.board.Board;
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
@Table(schema = "${database.name}", name = "columns")
public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id", unique = true, nullable = false)
    private int columnId;


    @NotNull
    @Column(name = "column_title")
    private String columnTitle;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "position")
    private Integer position;

    @Column(name = "deleted")
    private boolean deleted;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board ;
}
