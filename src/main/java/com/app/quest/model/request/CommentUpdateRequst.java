package com.app.quest.model.request;

import jakarta.validation.constraints.Size;

public record CommentUpdateRequst(
        Long commentId,
        @Size(min = 1)
        String text
) {
}
