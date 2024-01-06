package com.myfitbody.controllers.contracts;

import com.myfitbody.controllers.HttpResponse;
import com.myfitbody.domain.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserController {

    ResponseEntity<HttpResponse<Boolean>> verifyEmail(UUID token);
    ResponseEntity<HttpResponse<UserResponseDTO>> register(UserCreateDTO dto);
    ResponseEntity<HttpResponse<Boolean>> updatePassword(UUID id, UserEditPasswordDTO dto);
    ResponseEntity<HttpResponse<UserResponseDTO>> updateEmail(UUID id, UserEditEmailDTO dto);
    ResponseEntity<HttpResponse<UserResponseDTO>> updateUser(UUID id, UserEditDTO dto);
    ResponseEntity<HttpResponse<UserResponseDTO>> getUserById(UUID id);
    ResponseEntity<HttpResponse<Page<UserResponseDTO>>> getAllUsers(Pageable pageable, String search);
    ResponseEntity<HttpResponse<Void>> deleteUser(UUID id);
}
