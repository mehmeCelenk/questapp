package com.app.quest.model.request;

public record LikeCreateRequest(
        String userId,
        Long   postId
) {
}
