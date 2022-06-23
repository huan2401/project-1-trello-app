package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.combinations.UserBoardRoleService;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("project1/api/user-board-role")
public class UserBoardRoleController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserBoardRoleService userBoardRoleService;

    @GetMapping(path = "/find-by-user")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id") int userId)
    {
        return userService.findByUserId(userId).map(user -> {
            return userBoardRoleService.findByUser(userId).isEmpty()
                    ?ResponseEntity.noContent().build()
                    :ResponseEntity.ok(userBoardRoleService.findByUser(userId));
        }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/find-by-board")
    public ResponseEntity<?> findByBoard(@RequestParam(name = "board_id")int boardId)
    {
        return boardService.findByBoardId(boardId).map(board -> {
            return userBoardRoleService.findByBoard(boardId).isEmpty()
                    ?ResponseEntity.noContent().build()
                    :ResponseEntity.ok(userBoardRoleService.findByBoard(boardId));
        }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/find-by-user-and-board")
    public ResponseEntity<?> findByUserAndBoard(@RequestParam(name = "user_id")int userId,
                                                @RequestParam(name ="board_id")int boardId)
    {
        return userService.findByUserId(userId).map(user -> {
            return boardService.findByBoardId(boardId).map(board -> {
                return userBoardRoleService.findByUserAndBoard(userId,boardId).isPresent()
                        ? ResponseEntity.ok(userBoardRoleService.findByUserAndBoard(userId,boardId))
                        : ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.noContent().build());
        }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/add")
    public ResponseEntity<?> add(@RequestParam(name = "user_id") int userId,
                                 @RequestParam(name = "board_id") int boardId,
                                 @RequestParam(name = "role_id") int roleId)
    {
        UserBoardRole userBoardRoleToAdd = UserBoardRole.builder().build();
        Optional<Role> roleOptional = roleService.findByRoleId(roleId);
        if (roleOptional.isPresent()) userBoardRoleToAdd.setRole(roleOptional.get());
            return userService.findByUserId(userId).map(user -> {
            userBoardRoleToAdd.setUser(user);
            return boardService.findByBoardId(boardId).map(board -> {
                userBoardRoleToAdd.setBoard(board);
                Optional<UserBoardRole> userBoardRoleAdded = userBoardRoleService.add(userBoardRoleToAdd);
                return userBoardRoleAdded.isPresent()?ResponseEntity.ok(userBoardRoleAdded)
                        : ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.noContent().build());
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping(path = "/delete-by-board")
    public ResponseEntity<?> deleteByBoard(@RequestParam(name = "board_id") int boardId)
    {
        return boardService.findByBoardId(boardId).map(board -> {
            return userBoardRoleService.deleteByBoard(boardId)
                    ?ResponseEntity.status(200).build()
                    :ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping(path = "/delete-user-from-board")
    public ResponseEntity<?> deleteUserFromBoard(@RequestParam(name = "board_id") int boardId,
                                                 @RequestParam(name = "user_id") int userId)
    {
        return userService.findByUserId(userId).map(user -> {
            return boardService.findByBoardId(boardId).map(board -> {
               return userBoardRoleService.deleteUserFromBoard(userId,boardId)
                       ? ResponseEntity.status(200).build()
                       : ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.noContent().build());
        }).orElse(ResponseEntity.noContent().build());
    }


}
