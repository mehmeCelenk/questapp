package com.app.quest.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotNull
        String title,

        @NotNull
        @Size(min = 1, max = 255)
        String text,

        String userId
) {
}
