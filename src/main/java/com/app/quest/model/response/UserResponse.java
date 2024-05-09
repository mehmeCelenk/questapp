package com.app.quest.model.response;

import com.app.quest.model.entity.User;

public record UserResponse(
        String email,
        String username
        ) {

    
    public static UserResponse convert(User user) {
        return new UserResponse(user.getEmail(), user.getUsername());
    }
}
