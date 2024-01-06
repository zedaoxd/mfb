package com.myfitbody.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserEditDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName) {
}
