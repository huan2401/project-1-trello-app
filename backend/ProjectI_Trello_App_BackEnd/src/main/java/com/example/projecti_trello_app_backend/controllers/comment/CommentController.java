package com.example.projecti_trello_app_backend.controllers.comment;

import com.example.projecti_trello_app_backend.dto.CommentDTO;
import com.example.projecti_trello_app_backend.entities.comment.Comment;
import com.example.projecti_trello_app_backend.services.comment.CommentService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import com.example.projecti_trello_app_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("project1/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;


    @GetMapping("/find-by-task")
    public ResponseEntity<?> findByTask(@RequestParam(name = "task_id")int taskId)
    {
        return taskService.findByTaskId(taskId).map(task ->
            ResponseEntity.ok(commentService.findByTask(taskId))
        ).orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Comment comment,
                                 @RequestParam(name = "task_id")int taskId,
                                 @RequestParam(name = "user_id") int userId)
    {
        return taskService.findByTaskId(taskId).map(task -> {
            comment.setTask(task);
            return userService.findByUserId(userId).map(user -> {
                comment.setUser(user);
                return ResponseEntity.ok(commentService.add(comment));
            }).orElse(ResponseEntity.status(304).build());
        }).orElse(ResponseEntity.status(304).build());
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> edit(@RequestParam(name = "commnnt_id")int commentId,
                                  @RequestBody CommentDTO commentDTO)
    {
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/delete-by-comment")
    public ResponseEntity<?> deleteByComment(@RequestParam(name = "comment_id")int commentId)
    {
        return commentService.findByCommentId(commentId).isPresent()?ResponseEntity.ok(commentService.deleteByComment(commentId))
                                                                    :ResponseEntity.status(304).build();
    }







}
