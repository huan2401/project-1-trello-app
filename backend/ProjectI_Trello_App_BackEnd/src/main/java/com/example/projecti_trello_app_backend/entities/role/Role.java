package com.example.projecti_trello_app_backend.entities.role;

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
@Table(schema = "${database.name}", name = "role")
public class Role { // role in a board

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name", nullable = false)
    @NotNull
    private String roleName;

    @Column(name = "role_type",nullable = false)
    @NotNull
    private String roleType;

    @Column(name = "deleted")
    private boolean deleted;


}
