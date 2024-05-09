package com.app.quest.model.request;

import com.app.quest.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserCreateRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 1, max = 10)
        String username,

        @Size(min = 6)
        String password,
        Set<Role> authorities
) {
}
