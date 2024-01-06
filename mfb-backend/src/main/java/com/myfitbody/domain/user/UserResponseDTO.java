package com.myfitbody.domain.user;

import com.myfitbody.domain.role.RoleResponseDTO;

import java.util.List;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String inactiveMessage,
        boolean isActive,
        List<RoleResponseDTO> roles) {
}
