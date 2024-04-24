package com.app.quest.model.response;

public record LikeResponse(
        String userId,
        Long postId
) {
    public static LikeResponse convert(String userId, Long postId) {
        return new LikeResponse(userId, postId);
    }
}
