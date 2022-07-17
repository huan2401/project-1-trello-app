package com.example.projecti_trello_app_backend.entities.combinations;

import com.example.projecti_trello_app_backend.entities.column.Columns;
import com.example.projecti_trello_app_backend.entities.task.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "${database.name}", name = "column_task")
public class ColumnTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private Columns column;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "position")
    private int position;

    @Column(name = "stage")
    private boolean stage; // đánh dấu trạng thái của 1 task ở 1 column (có hay không)

}
