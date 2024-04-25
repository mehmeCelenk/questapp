package com.app.quest.repository;

import com.app.quest.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUserUUIDAndPostId(String userId, Long postId);
    List<Comment> findByPostId(Long postId);
    List<Comment> findByUserUUID(String userId);
}
