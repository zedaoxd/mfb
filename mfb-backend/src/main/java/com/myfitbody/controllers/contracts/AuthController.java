package com.myfitbody.controllers.contracts;

import com.myfitbody.controllers.HttpResponse;
import com.myfitbody.domain.user.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthController {

    ResponseEntity<HttpResponse<UserLoginResponseDTO>> login(UserLoginDTO dto);
    ResponseEntity<HttpResponse<UserResponseDTO>> register(UserCreateDTO dto);
    ResponseEntity<HttpResponse<Boolean>> verifyEmail(UUID token);
    ResponseEntity<HttpResponse<Boolean>> tokenResetPassword(String email);
    ResponseEntity<HttpResponse<UserResponseDTO>> resetPassword(UUID token, UserResetPasswordDTO dto);
}
