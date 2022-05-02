package com.example.projecti_trello_app_backend.controllers.column;

import com.example.projecti_trello_app_backend.dto.ColumnDTO;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.column.Columns;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import com.example.projecti_trello_app_backend.services.combinations.ColumnTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("project1/api/column")
public class ColumnController {

    @Autowired
    private  ColumnService columnService;

    @Autowired
    private BoardService boardService;


    @GetMapping("/find-all-by-board")
    public ResponseEntity<?> findAllByBoard(@RequestParam(name = "board_id") int boardId)
    {
        return boardService.findByBoardId(boardId).isPresent()?ResponseEntity.ok(columnService.findAllByBoard(boardId))
                                                              :ResponseEntity.ok(Optional.empty());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addColumn(@RequestBody Columns column, @RequestParam(name = "board_id") int boardId)
    {
        if(!boardService.findByBoardId(boardId).isPresent()) return ResponseEntity.ok(Optional.empty());
        column.setBoard(boardService.findByBoardId(boardId).get());
        return ResponseEntity.ok(columnService.add(column));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateColumn(@RequestBody ColumnDTO columnDTO,
                                          @RequestParam(name = "column_id") int columId)
    {
        if(!columnService.findByColumnId(columId).isPresent()) return ResponseEntity.ok(Optional.empty());
        columnDTO.setColumnId(columId);
        return ResponseEntity.ok(columnService.update(columnDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete (@RequestParam(name = "column_id")int columnId)
    {
        return columnService.delete(columnId)?ResponseEntity.status(200).build()
                                            :ResponseEntity.status(304).build();
    }



}
