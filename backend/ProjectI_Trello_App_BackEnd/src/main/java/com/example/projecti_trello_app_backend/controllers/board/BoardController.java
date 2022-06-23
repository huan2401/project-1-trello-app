package com.example.projecti_trello_app_backend.controllers.board;

import com.example.projecti_trello_app_backend.dto.BoardDTO;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.workspace.Workspace;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.services.workspace.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.event.MailEvent;
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

//    @GetMapping("find-all-by-manager")
//    public ResponseEntity<?> findAllByManager(@RequestParam("manager_id") Integer managerId)
//    {
//        return userService.findByUserId(managerId).map(user -> {
//            return ResponseEntity.ok(boardService.findAllByUser(user.getUserId()));
//        }).orElse(ResponseEntity.noContent().build());
//    }
    
    @PostMapping("/add-board")
    public ResponseEntity<?> addBoard(@RequestBody Board board,
                                      @RequestParam(name = "work_space_id")int workSpaceId){
           return workspaceService.findByWorkspaceId(workSpaceId).map(workspace -> {
               board.setWorkspace(workspace);
               return boardService.addBoard(board).isPresent()
                       ?ResponseEntity.status(200).body(new MessageResponse("Add new board successfully"))
                       :ResponseEntity.status(204).body(new MessageResponse("Add new board fail"));
           }).orElse(ResponseEntity.status(204).body(new MessageResponse("Workspace not found")));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody BoardDTO boardDTO,
                                    @RequestParam(name = "board_id") int boardId) //khi cần có thay đổi về PM
    {
        boardDTO.setBoardId(boardId);
        return boardService.update(boardDTO).isPresent()
                ?ResponseEntity.ok(new MessageResponse("Update board successfully"))
                :ResponseEntity.status(204).body(new MessageResponse("Update board fail"));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "board_id")int boardId)
    {
        return boardService.delete(boardId)
                ? ResponseEntity.ok(new MessageResponse("Delete board successfully"))
                : ResponseEntity.status(204).body(new MessageResponse("Delete board fail"));
    }

    @DeleteMapping(path = "/delete-by-work-space")
    public ResponseEntity<?> deleteByWorkSpace(@RequestParam(name = "workspace_id")int workspaceId)
    {
        return boardService.deleteByWorkspace(workspaceId)
                ? ResponseEntity.ok(new MessageResponse("Delete board successfully"))
                : ResponseEntity.status(204).body(new MessageResponse("Delete board fail"));
    }
}
