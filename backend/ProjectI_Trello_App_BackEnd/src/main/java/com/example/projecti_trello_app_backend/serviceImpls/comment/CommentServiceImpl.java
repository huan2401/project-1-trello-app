package com.example.projecti_trello_app_backend.serviceImpls.comment;

import com.example.projecti_trello_app_backend.dto.CommentDTO;
import com.example.projecti_trello_app_backend.entities.comment.Comment;
import com.example.projecti_trello_app_backend.repositories.comment.CommentRepo;
import com.example.projecti_trello_app_backend.repositories.user.UserRepo;
import com.example.projecti_trello_app_backend.services.comment.CommentService;
import com.example.projecti_trello_app_backend.services.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;


    @Override
    public List<Comment> findByTask(int taskId) {
        try{
            return commentRepo.findByTask(taskId);
        }catch (Exception ex)
        {
            log.error("find comment by task error",ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Comment> findByCommentId(int commentId) {
        try{
             return commentRepo.findByCommentId(commentId).map(comment -> {
                return commentRepo.findByCommentId(commentId);
            }).orElse(Optional.empty());
        } catch (Exception ex)
        {
            log.error("find comment by id error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Comment> add(Comment comment) {
        try{
            comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            return Optional.ofNullable(commentRepo.save(comment));
        } catch (Exception ex)
        {
            log.error("add comment error",ex);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Comment> update(CommentDTO commentDTO) {
        try{
            Comment commentToUpDate = commentRepo.findByCommentId(commentDTO.getCommentId()).get();
            commentToUpDate.setEdited(true);
            commentToUpDate.setEditedAt(new Timestamp(System.currentTimeMillis()));
            commentToUpDate.setContent(commentDTO.getContent()!=null? commentDTO.getContent():commentToUpDate.getContent());
            return Optional.ofNullable(commentRepo.save(commentToUpDate));
        } catch (Exception ex)
        {
            log.error("update comment error",ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteByComment(int commentId) {
        try
        {
            return commentRepo.deleteByComment(commentId)>0?true:false;
        } catch (Exception ex)
        {
            log.error("delete comment by comment id",ex);
            return false;
        }
    }

    @Override
    public boolean deleteByTask(int taskId) {
        try{
            return commentRepo.deleteByTask(taskId)>0?true:false;
        }catch (Exception ex)
        {
            log.error("delete commnet by task error",ex);
            return false;
        }
    }
}
