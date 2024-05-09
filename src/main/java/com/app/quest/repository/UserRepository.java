package com.app.quest.repository;

import com.app.quest.model.entity.User;
import com.app.quest.model.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getByUsername(String username);
}
