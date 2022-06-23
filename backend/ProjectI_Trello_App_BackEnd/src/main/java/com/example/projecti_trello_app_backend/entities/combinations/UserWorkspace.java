package com.example.projecti_trello_app_backend.entities.combinations;

import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}", name = "user_workspace")
public class UserWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "work_space_id",nullable = false)
    @NotNull
    private Workspace workspace;

    @Column(name = "is_creator")
    private boolean isCreator;

    @Column(name = "deleted")
    private boolean deleted;
}
