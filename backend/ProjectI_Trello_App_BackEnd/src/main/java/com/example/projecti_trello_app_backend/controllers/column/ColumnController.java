package com.example.projecti_trello_app_backend.controllers.column;

import com.example.projecti_trello_app_backend.dto.ColumnDTO;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.column.Columns;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.column.ColumnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Tag(name = "Column Controller")
@RestController
@RequestMapping(value = "project1/api/column")
public class ColumnController {

    @Autowired
    private  ColumnService columnService;

    @Autowired
    private BoardService boardService;

    @Operation(summary = "Find all columns of a board")
    @GetMapping("/find-all-by-board")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findAllByBoard(@RequestParam(name = "board_id") int boardId,
                                            HttpServletRequest request)
    {
        return boardService.findByBoardId(boardId).isPresent()
                ?ResponseEntity.ok(columnService.findAllByBoard(boardId))
                :ResponseEntity.ok(new MessageResponse("This board doesn't has any columns",200));
    }

    @Operation(summary = "Add a column to a board")
    @PostMapping("/add")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> addColumn(@RequestBody Columns column,
                                                    @RequestParam(name = "board_id") int boardId,
                                                    HttpServletRequest request)
    {
        if(!boardService.findByBoardId(boardId).isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("Board not found",204));
        column.setBoard(boardService.findByBoardId(boardId).get());
        return columnService.add(column).isPresent()
                ?ResponseEntity.ok(new MessageResponse("Add new column successfully",200))
                :ResponseEntity.status(204).body(new MessageResponse("Add new column fail",204));
    }

    @Operation(summary = "Update a column's infomation")
    @PutMapping("/update")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> updateColumn(@RequestBody ColumnDTO columnDTO,
                                          @RequestParam(name = "column_id") int columId,
                                          HttpServletRequest request)
    {
        if(!columnService.findByColumnId(columId).isPresent()) return ResponseEntity.ok(Optional.empty());
        columnDTO.setColumnId(columId);
        return columnService.update(columnDTO).isPresent()
                ?ResponseEntity.ok(new MessageResponse("Update column successfully",200))
                :ResponseEntity.status(304).body(new MessageResponse("Update column fail",304));
    }

    @Operation(summary = "Delete a column by id")
    @DeleteMapping("/delete")
    @RequireBoardAdmin
    @SecurityRequirement(name = "methodBearerAuth")
    public synchronized ResponseEntity<?> delete (@RequestParam(name = "column_id")int columnId,
                                     HttpServletRequest request)
    {
        return columnService.delete(columnId)
                ?ResponseEntity.ok(new MessageResponse("Delete column successfully",200))
                :ResponseEntity.status(304).body(new MessageResponse("Delete column fail",304));
    }
}
