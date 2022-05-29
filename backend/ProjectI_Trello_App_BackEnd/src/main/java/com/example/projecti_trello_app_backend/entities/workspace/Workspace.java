package com.example.projecti_trello_app_backend.entities.workspace;

import com.example.projecti_trello_app_backend.entities.user.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}",name = "work_space")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_space_id")
    private int workSpaceId;

    @Column(name = "work_space_name")
    @NotNull
    private String workSpaceTitle;

    @Column(name = "work_space_type")
    private String workSpaceType;

    @Column(name ="work_space_description")
    private String workSpaceDescription;

    @ManyToOne
    @JoinColumn(name = "work_space_creator_id")
    @NotNull
    private User workSpaceCreator;

    @Column(name = "deleted")
    private boolean deleted;


}
