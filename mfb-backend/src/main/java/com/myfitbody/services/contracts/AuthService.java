package com.myfitbody.services.contracts;

import com.myfitbody.domain.user.*;

import java.util.UUID;

public interface AuthService  {

    UserResponseDTO register(UserCreateDTO dto);
    boolean verifyEmail(UUID token);
    UserLoginResponseDTO loginUser(UserLoginDTO dto);
    boolean tokenResetPassword(String email);
    UserResponseDTO resetPassword(UUID token, UserResetPasswordDTO dto);
}
