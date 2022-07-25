package com.example.projecti_trello_app_backend.controllers.combinations;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import com.example.projecti_trello_app_backend.entities.combinations.UserWorkspace;
import com.example.projecti_trello_app_backend.entities.role.Role;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.combinations.UserBoardRoleService;
import com.example.projecti_trello_app_backend.services.combinations.UserWorkspaceService;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name = "UserBoardRole Controller",
        description = "Use to represent the relationship between users and boards with diffenrent roles")
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

    @Autowired
    private UserWorkspaceService userWorkspaceService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Find all user_board_role_s of a user")
    @GetMapping(path = "/find-by-user")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByUser(@RequestParam(name = "user_id") int userId,
                                        HttpServletRequest request)
    {
        return userService.findByUserId(userId).map(user -> {
            return userBoardRoleService.findByUser(userId).isEmpty()
                    ?ResponseEntity.noContent().build()
                    :ResponseEntity.ok(userBoardRoleService.findByUser(userId));
        }).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Find all my user_board_role_s")
    @SecurityRequirement(name = "methodBearerAuth")
    @GetMapping("/find-my-board")
    public ResponseEntity<?> findMyBoards(HttpServletRequest request)
    {
        User user = util.getUserFromRequest(request);
        if(user == null) return ResponseEntity.status(204).body(new MessageResponse("User is not found"));
        return ResponseEntity.ok(userBoardRoleService.findByUser(user.getUserId()));
    }

    @Operation(summary = "Find all user_board_role_s of board")
    @GetMapping(path = "/find-by-board")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByBoard(@RequestParam(name = "board_id")int boardId,
                                         HttpServletRequest request)
    {
        return boardService.findByBoardId(boardId).map(board -> {
            return userBoardRoleService.findByBoard(boardId).isEmpty()
                    ?ResponseEntity.noContent().build()
                    :ResponseEntity.ok(userBoardRoleService.findByBoard(boardId));
        }).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Find user_board_role  by both a user and a board")
    @GetMapping(path = "/find-by-user-and-board")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByUserAndBoard(@RequestParam(name = "user_id")int userId,
                                                @RequestParam(name ="board_id")int boardId,
                                                HttpServletRequest request)
    {
        return userService.findByUserId(userId).map(user -> {
            return boardService.findByBoardId(boardId).map(board -> {
                return userBoardRoleService.findByUserAndBoard(userId,boardId).isPresent()
                        ? ResponseEntity.ok(userBoardRoleService.findByUserAndBoard(userId,boardId))
                        : ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.noContent().build());
        }).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Add a new user to a board <=> Add a new user_board_role")
    @GetMapping(path = "/add")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> add(@RequestParam(name = "user_id") int userId,
                                              @RequestParam(name = "board_id") int boardId,
                                              @RequestParam(name = "role_name") String roleName,
                                              HttpServletRequest request)
    {
        if(userBoardRoleService.findByUserAndBoard(userId,boardId).isPresent())
            return ResponseEntity.status(304).body(new MessageResponse("User is in board"));
        String roleNameSta = "BOARD_"+roleName.toUpperCase(); // stadardize role' name
        UserBoardRole userBoardRoleToAdd = UserBoardRole.builder().build();
        Optional<Role> roleOptional = roleService.findByRoleName(roleNameSta);
        if (roleOptional.isPresent()) userBoardRoleToAdd.setRole(roleOptional.get());
        else return ResponseEntity.status(304).body(new MessageResponse("Add user to board fail - role not found"));
        return userService.findByUserId(userId).map(user -> {
            userBoardRoleToAdd.setUser(user);
            return boardService.findByBoardId(boardId).map(board -> {
                userBoardRoleToAdd.setBoard(board);
                Optional<UserBoardRole> userBoardRoleAdded = userBoardRoleService.add(userBoardRoleToAdd);
                if(!userWorkspaceService.existsByUserAndWorkspace(userId,board.getWorkspace().getWorkspaceId()))
                {
                    UserWorkspace userWorkspace = UserWorkspace.builder().build();
                    userWorkspace.setWorkspace(workspaceService.findByWorkspaceId(board.getWorkspace().getWorkspaceId()).get());
                    userWorkspace.setUser(user);
                    userWorkspace.setRole(roleService.findByRoleName("WS_GUESS").get());
                    if(!userWorkspaceService.add(userWorkspace).isPresent())
                        return ResponseEntity.status(304).body(new MessageResponse("Set new guess user of workspace fail"));
                }
                return userBoardRoleAdded.isPresent()
                        ?ResponseEntity.status(200).body(new MessageResponse("Add user to board successfully"))
                        : ResponseEntity.status(204).body(new MessageResponse("Add user to board fail"));
            }).orElse(ResponseEntity.status(304).body(new MessageResponse("Add user to board fail - board not found")));
        }).orElse(ResponseEntity.status(304).body(new MessageResponse("Add user to board fail - user not found")));
    }

    @Operation(summary = "Set role for a user in a board")
    @PutMapping(path = "/set-role-for-user")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> setRoleForUser(@RequestParam(name = "user_id")int userId,
                                                         @RequestParam(name = "board_id")int boardId,
                                                         @RequestParam(name = "role_name") String roleName,
                                                         HttpServletRequest request)
    {
        String roleNameSta = "BOARD_"+roleName.toUpperCase();
        if(!roleService.findByRoleName(roleNameSta).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("Not found role"));
        if(!userService.findByUserId(userId).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("Not found user"));
        if(!boardService.findByBoardId(boardId).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("Not found board"));
        return userBoardRoleService.setRoleForUser(userId,boardId,roleNameSta)
                ?ResponseEntity.status(200).body(new MessageResponse("Set role in board for user successfully"))
                : ResponseEntity.status(304).body(new MessageResponse("Set role in board for user fail"));
    }

    @Operation(summary = "Delete all user_board_role of a board after it was deleted")
    @DeleteMapping(path = "/delete-by-board")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> deleteByBoard(@RequestParam(name = "board_id") int boardId,
                                                        HttpServletRequest request)
    {
        return boardService.findByBoardId(boardId).map(board -> {
            return userBoardRoleService.deleteByBoard(boardId)
                    ?ResponseEntity.status(200).body(new MessageResponse("Delete by board successfully"))
                    :ResponseEntity.status(304).body(new MessageResponse("Delete by board fail"));
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("Delete by board fail - Not found board")));
    }

    @Operation(summary = "Remove a board's member from the board")
    @DeleteMapping(path = "/remove-user-from-board")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> removeUserFromBoard(@RequestParam(name = "board_id") int boardId,
                                                              @RequestParam(name = "user_id") int userId,
                                                              HttpServletRequest request)
    {
        return userService.findByUserId(userId).map(user -> {
            return boardService.findByBoardId(boardId).map(board -> {
                return userBoardRoleService.deleteUserFromBoard(userId,boardId)
                        ? ResponseEntity.status(200).body(new MessageResponse("Delete user from board successfully"))
                        : ResponseEntity.status(304).body(new MessageResponse("Delete user from board fail"));
            }).orElse(ResponseEntity.status(204).body(new MessageResponse("Delete user from board fail - board not found")));
        }).orElse(ResponseEntity.status(304).body(new MessageResponse("Delete user from board fail - user not found")));
    }

}
