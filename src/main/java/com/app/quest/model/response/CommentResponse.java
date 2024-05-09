package com.app.quest.model.response;

import com.app.quest.model.entity.Comment;

public record CommentResponse(
        String text,
        String userId,
        Long postId
) {

    public static CommentResponse convert(Comment comment){
        return new CommentResponse(comment.getText(), comment.getUser().getUsername(), comment.getPost().getId());
    }
}
