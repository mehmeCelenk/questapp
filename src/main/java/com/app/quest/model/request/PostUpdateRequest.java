package com.app.quest.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        Long id,
        @NotNull
        String title,

        @NotNull
        @Size(min = 1, max = 255)
        String text
) {
}
