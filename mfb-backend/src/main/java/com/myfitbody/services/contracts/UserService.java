package com.myfitbody.services.contracts;

import com.myfitbody.domain.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<UserResponseDTO> getAllUsers(Pageable pageable, String search);
    UserResponseDTO getUserById(UUID id);
    UserResponseDTO createUser(UserCreateDTO dto);
    UserResponseDTO updateUser(UUID id, UserEditDTO dto);
    boolean updateUserPassword(UUID id, UserEditPasswordDTO dto);
    UserResponseDTO updateUserEmail(UUID id, UserEditEmailDTO dto);
    boolean verifyEmail(UUID token);
    void deleteUser(UUID id);
    boolean tokenResetPassword(String email);
    UserResponseDTO resetPassword(UUID token, UserResetPasswordDTO dto);
}
