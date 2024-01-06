package com.myfitbody.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserEditPasswordDTO(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword) {
}
