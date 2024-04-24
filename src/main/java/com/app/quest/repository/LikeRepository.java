package com.app.quest.repository;

import com.app.quest.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUserUUIDAndPostId(String userId, Long postId);
    List<Like> findByPostId(Long postId);
    List<Like> findByUserUUID(String userId);
}
