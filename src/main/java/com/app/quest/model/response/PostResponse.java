package com.app.quest.model.response;

import com.app.quest.model.entity.Post;

import java.util.List;

public record PostResponse(
        String title,
        String text,
        String userName,
        List<LikeResponse> postLikes
) {
    public static PostResponse convert(Post post, List<LikeResponse> likes) {
        return new PostResponse(post.getTitle(), post.getText(), post.getUser().getUsername(), likes);
    }
}
