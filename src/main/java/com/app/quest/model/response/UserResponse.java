package com.app.quest.model.response;

import com.app.quest.model.entity.User;

public record UserResponse(
        String username
        ) {

    
    public static UserResponse convert(User newUser) {
        return new UserResponse(newUser.getUsername());
    }
}
