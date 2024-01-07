package com.myfitbody.domain.user;

import com.myfitbody.validations.UserResetPasswordValid;
import jakarta.validation.constraints.NotBlank;

@UserResetPasswordValid
public record UserResetPasswordDTO(
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword
) {
}
