package com.app.quest.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotNull
        @Size(min = 1, max = 10)
        String username,

        @Size(min = 6)
        String password
) {
}
