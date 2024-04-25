package com.app.quest.model.response;

public record CommentResponse(
        String text,
        String userId,
        Long postId
) {

    public static CommentResponse convert(String text, String userId, Long postId){
        return new CommentResponse(text, userId, postId);
    }
}
