package com.app.quest.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateRequest(
        @Size(min = 1)
        String text,
        @NotNull
        String userId,
        @NotNull
        Long postId
) {
}
