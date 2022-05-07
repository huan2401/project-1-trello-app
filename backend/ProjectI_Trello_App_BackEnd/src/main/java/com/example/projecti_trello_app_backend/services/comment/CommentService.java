package com.example.projecti_trello_app_backend.services.comment;

import com.example.projecti_trello_app_backend.dto.CommentDTO;
import com.example.projecti_trello_app_backend.entities.comment.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {

    List<Comment> findByTask(int taskId);

    Optional<Comment> findByCommentId(int commentId);

    Optional<Comment> add (Comment comment);

    Optional<Comment> update (CommentDTO commentDTO);

    boolean deleteByComment(int commentId);

    boolean deleteByTask (int taskId);
}
