package com.example.projecti_trello_app_backend.controllers.comment;

import com.example.projecti_trello_app_backend.dto.CommentDTO;
import com.example.projecti_trello_app_backend.dto.MessageResponse;
import com.example.projecti_trello_app_backend.entities.comment.Comment;
import com.example.projecti_trello_app_backend.security.authorization.RequireCommentCreator;
import com.example.projecti_trello_app_backend.services.comment.CommentService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import com.example.projecti_trello_app_backend.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Comment Controller")
@RestController
@RequestMapping("project1/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityUtils util;

    @Operation(summary = "Find all comments of a task")
    @GetMapping("/find-by-task")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> findByTask(@RequestParam(name = "task_id")int taskId,
                                        HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task ->
            ResponseEntity.ok(commentService.findByTask(taskId))
        ).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Add a new comment of a user to a task")
    @PostMapping("/add")
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> add(@RequestBody Comment comment,
                                 @RequestParam(name = "task_id")int taskId,
                                 @RequestParam(name = "user_id") int userId,
                                 HttpServletRequest request)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            comment.setTask(task);
            return userService.findByUserId(userId).map(user -> {
                comment.setUser(user);
                return commentService.add(comment).isPresent()
                        ?ResponseEntity.status(200).body(new MessageResponse("Add comment successfully"))
                        :ResponseEntity.status(304).body(new MessageResponse("Add comment fail"));
            }).orElse(ResponseEntity.status(204).body(new MessageResponse("Add comment fail-User not found")));
        }).orElse(ResponseEntity.status(204).body(new MessageResponse("Add comment fail-Task not found")));
    }

    @Operation(summary = "Edit a comment of a user in a task")
    @PutMapping(path = "/edit")
    @RequireCommentCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> edit(@RequestParam(name = "comment_id")int commentId,
                                  @RequestBody CommentDTO commentDTO,
                                  HttpServletRequest request)
    {
        if(!util.checkUserComment(request,commentId))
            return ResponseEntity.status(403).body(new MessageResponse("User don't have permission to edit comment"));
        if(!commentService.findByCommentId(commentId).isPresent())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Not found comment"));
        return commentService.update(commentDTO).isPresent()
                ?ResponseEntity.status(200).body(new MessageResponse("Edit comment successfully"))
                :ResponseEntity.status(304).body(new MessageResponse("Edit comment fail"));
    }

    @Operation(summary = "Delete by comment's id")
    @DeleteMapping("/delete-by-comment")
    @RequireCommentCreator
    @SecurityRequirement(name = "methodBearerAuth")
    public ResponseEntity<?> deleteByComment(@RequestParam(name = "comment_id")int commentId,
                                             HttpServletRequest request)
    {
        return commentService.findByCommentId(commentId).isPresent()
                && commentService.deleteByComment(commentId)
                ?ResponseEntity.status(200).body(new MessageResponse("Delete comment by id successfully"))
                :ResponseEntity.status(304).body(new MessageResponse("Delete comment by id fail"));
    }
}
