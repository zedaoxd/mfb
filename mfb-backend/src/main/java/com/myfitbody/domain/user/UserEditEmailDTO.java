package com.myfitbody.domain.user;

import com.myfitbody.validations.UserEditEmailValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@UserEditEmailValid
public record UserEditEmailDTO(
        @NotBlank
        @Email
        String email) {
}
