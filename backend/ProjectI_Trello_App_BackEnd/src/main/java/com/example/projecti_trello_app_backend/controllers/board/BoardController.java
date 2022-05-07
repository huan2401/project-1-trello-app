package com.example.projecti_trello_app_backend.controllers.board;

import com.example.projecti_trello_app_backend.dto.BoardDTO;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("project1/api/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @GetMapping("find-all-by-manager")
    public ResponseEntity<?> findAllByManager(@RequestParam("manager_id") Integer managerId)
    {
        return userService.findByUserId(managerId).map(user -> {
            return ResponseEntity.ok(boardService.findAllByUser(user.getUserId()));
        }).orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/add-board")
    public ResponseEntity<?> addBoard(@RequestBody Board board, @RequestParam("manager_id") Integer managerId){
            return userService.findByUserId(managerId).map(manager ->{
                board.setBoardManager(manager);
                return ResponseEntity.ok(boardService.addBoard(board));
            }).orElse(ResponseEntity.ok(Optional.empty()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody BoardDTO boardDTO,
                                    @RequestParam(name = "board_id") int boardId,
                                    @RequestParam(name = "manager_id", required = false) Integer managerId) //khi cần có thay đổi về PM
    {
        if(managerId!=null) boardDTO.setBoardManagerId(managerId);
        else boardDTO.setBoardManagerId(0);
        boardDTO.setBoardId(boardId);
        return ResponseEntity.ok(boardService.update(boardDTO));
    }



}
