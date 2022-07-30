package com.example.projecti_trello_app_backend.entities.combinations;

import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "${database.name}",name = "user_board_role")
public class UserBoardRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id",nullable = false)
    @NotNull
    private Board board;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @NotNull
    private Role role;

    @Column(name = "deleted")
    private boolean deleted;


}
