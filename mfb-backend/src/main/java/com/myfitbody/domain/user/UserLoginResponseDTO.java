package com.myfitbody.domain.user;

import lombok.Builder;

@Builder
public record UserLoginResponseDTO(
        String token,
        UserResponseDTO user
) {
}
