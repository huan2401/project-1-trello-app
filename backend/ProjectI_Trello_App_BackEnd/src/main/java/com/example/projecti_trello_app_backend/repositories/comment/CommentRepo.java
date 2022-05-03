package com.example.projecti_trello_app_backend.repositories.comment;

import com.example.projecti_trello_app_backend.entities.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {

    @Query(value = "from Comment cmt where cmt.commentId = ?1 and cmt.deleted  =false ")
     Optional<Comment> findByCommentId(int commentId);

    @Query (value ="from Comment cmt where cmt.task.taskId =?1 and cmt.deleted =false")
     List<Comment> findByTask(int taskId);

    @Modifying
    @Transactional
    @Query(value = "update Comment cmt set cmt.deleted =true " +
            " where cmt.commentId =?1 and cmt.deleted=false")
     int deleteByComment(int commentId);

    @Modifying
    @Transactional
    @Query(value = "update Comment cmt set cmt.deleted=true " +
            " where cmt.task.taskId=?1 and cmt.deleted=false")
    int deleteByTask(int taskId);



}
