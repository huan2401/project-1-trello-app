package com.example.projecti_trello_app_backend.controllers.upload_files;

import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.board.Board;
import com.example.projecti_trello_app_backend.entities.task.Task;
import com.example.projecti_trello_app_backend.entities.user.User;
import com.example.projecti_trello_app_backend.security.authorization.RequireBoardAdmin;
import com.example.projecti_trello_app_backend.services.board.BoardService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.upload_files.CloudinaryService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Optional;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Upload Image Controller")
@RestController
@RequestMapping(path = "project1/api/upload")
public class CloudinaryController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Upload user's avatar")
    @PostMapping(path = "/avatar-upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    @SecurityRequirement(name = "methodBearerAuth")
    @ApiResponse(content = {@Content(mediaType = "multipart/form-data")})
    public ResponseEntity<?> uploadAvatar (@RequestParam(name = "avatar")MultipartFile avatar,
                                           MultipartHttpServletRequest request)
    {
        User user = util.getUserFromRequest(request);
        if(user==null)
            return ResponseEntity.status(204).body(new MessageResponse("User is not valid"));
        return ResponseEntity.ok(cloudinaryService.uploadAvatar(avatar,user.getUserId()));
    }

    @Operation(summary = "Upload task's background")
    @PostMapping(path = "/task-backgr-upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    @SecurityRequirement(name = "methodBearerAuth")
    @RequireBoardAdmin
    @ApiResponse(content = {@Content(mediaType = "multipart/form-data")})
    public ResponseEntity<?> uploadTaskBackground(@RequestParam(name = "task_backgr") MultipartFile file,
                                                  @RequestParam(name = "task_id")int taskId,
                                                  MultipartHttpServletRequest request)
    {
        Optional<Task> taskOptional = taskService.findByTaskId(taskId);
        if(!taskOptional.isPresent())
            return ResponseEntity.status(204).body(new MessageResponse("Task not found"));
        return ResponseEntity.ok(cloudinaryService.uploadTaskBackground(file,taskId));
    }

    @Operation(summary = "Uplaod board's background")
    @PostMapping(path = "/board-backgr-upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    @SecurityRequirement(name="methodBearerAuth")
    @RequireBoardAdmin
    @ApiResponse(content = {@Content(mediaType = "multipart/form-data")})
    public ResponseEntity<?> uploadBoardBackground(@RequestParam(name = "board_background") MultipartFile file,
                                                   @RequestParam(name = "board_id") int boardId,
                                                   MultipartHttpServletRequest request)

    {
        Optional<Board> boardOptional = boardService.findByBoardId(boardId);
        if(!boardOptional.isPresent()) return ResponseEntity.status(204).body(new MessageResponse("Board not found"));
        return ResponseEntity.ok(cloudinaryService.uploadTaskBackground(file,boardId));
    }

}
