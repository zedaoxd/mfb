package com.myfitbody.domain.user;

import com.myfitbody.domain.role.RoleRequestDTO;
import com.myfitbody.validations.UserCreateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@UserCreateValid
public record UserCreateDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        List<RoleRequestDTO> roles) {
}
