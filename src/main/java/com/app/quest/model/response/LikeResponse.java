package com.app.quest.model.response;

import com.app.quest.model.entity.Like;

public record LikeResponse(
        String userId,
        Long postId
) {
    public static LikeResponse convert(Like like) {
        return new LikeResponse(like.getUser().getUUID(), like.getPost().getId());
    }
}
