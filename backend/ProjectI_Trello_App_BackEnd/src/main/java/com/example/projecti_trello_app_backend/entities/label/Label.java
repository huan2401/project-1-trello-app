package com.example.projecti_trello_app_backend.entities.label;

import com.example.projecti_trello_app_backend.entities.task.Task;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "label")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_id", unique = true, nullable = false)
    private int labelId ;

    @Column(name = "label_color")
    private String labelColor;

    @Column(name = "label_title")
    private String labelTitle;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @NotNull
    private Task task;

    @Column(name = "deleted")
    private boolean deleted;

}
