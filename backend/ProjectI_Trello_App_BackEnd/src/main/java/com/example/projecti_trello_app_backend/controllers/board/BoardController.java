package com.example.projecti_trello_app_backend.controllers.board;

import com.example.projecti_trello_app_backend.dto.BoardDTO;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.combinations.UserBoardRole;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.security.authorization.RequireWorkspaceCreator;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.combinations.UserBoardRoleService;
import com.example.projecti_trello_app_backend.services.role.RoleService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.event.MailEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserBoardRoleService userBoardRoleService;

    @Autowired
    private SecurityUtils util;

    @Autowired
    private RoleService roleService;

//    @GetMapping("find-all-by-manager")
//    public ResponseEntity<?> findAllByManager(@RequestParam("manager_id") Integer managerId)
//    {
//        return userService.findByUserId(managerId).map(user -> {
//            return ResponseEntity.ok(boardService.findAllByUser(user.getUserId()));
//        }).orElse(ResponseEntity.noContent().build());
//    }
    
    @PostMapping("/add-board")
    @RequireWorkspaceCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> addBoard(@RequestBody Board board,
                                      @RequestParam(name = "work_space_id")int workSpaceId,
                                      HttpServletRequest request)
    {
        int boardAdmin = util.getUserFromRequest(request).getUserId();
        UserBoardRole userBoardRole = UserBoardRole.builder().build();
        userBoardRole.setRole(roleService.findByRoleName("BOARD_ADMIN").get());
        userBoardRole.setUser(userService.findByUserId(boardAdmin).get());
           return workspaceService.findByWorkspaceId(workSpaceId).map(workspace -> {
               board.setWorkspace(workspace);
               userBoardRole.setBoard(board);
               return boardService.addBoard(board).isPresent() && userBoardRoleService.add(userBoardRole).isPresent()
                       ?ResponseEntity.status(200).body(new MessageResponse("Add new board successfully"))
                       :ResponseEntity.status(204).body(new MessageResponse("Add new board fail"));
           }).orElse(ResponseEntity.status(204).body(new MessageResponse("Workspace not found")));
    }

    @PutMapping("/update")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> update(@RequestBody BoardDTO boardDTO,
                                    @RequestParam(name = "board_id") int boardId,
                                    HttpServletRequest request) //khi cần có thay đổi về PM
    {
        boardDTO.setBoardId(boardId);
        return boardService.update(boardDTO).isPresent()
                ?ResponseEntity.ok(new MessageResponse("Update board successfully"))
                :ResponseEntity.status(204).body(new MessageResponse("Update board fail"));
    }

    @DeleteMapping(path = "/delete")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> delete(@RequestParam(name = "board_id")int boardId,
                                    HttpServletRequest request)
    {
        return boardService.delete(boardId)
                ? ResponseEntity.ok(new MessageResponse("Delete board successfully"))
                : ResponseEntity.status(204).body(new MessageResponse("Delete board fail"));
    }

    @DeleteMapping(path = "/delete-by-work-space")
    @RequireWorkspaceCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> deleteByWorkSpace(@RequestParam(name = "workspace_id")int workSpaceId,
                                               HttpServletRequest request)
    {
        return boardService.deleteByWorkspace(workSpaceId)
                ? ResponseEntity.ok(new MessageResponse("Delete board successfully"))
                : ResponseEntity.status(204).body(new MessageResponse("Delete board fail"));
    }
}
