package com.myfitbody.controllers.impl;

import com.myfitbody.controllers.HttpResponse;
import com.myfitbody.controllers.contracts.UserController;
import com.myfitbody.domain.user.*;
import com.myfitbody.services.contracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @GetMapping("/verify-email/{token}")
    public ResponseEntity<HttpResponse<Boolean>> verifyEmail(@PathVariable UUID token) {
        boolean isSuccess = userService.verifyEmail(token);
        return ResponseEntity.ok(HttpResponse.<Boolean>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Email verified")
                .data(Map.of("isSuccess", isSuccess))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<UserResponseDTO>> getUserById(@PathVariable UUID id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(HttpResponse.<UserResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("User found")
                .data(Map.of("user", userResponseDTO))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<HttpResponse<Page<UserResponseDTO>>> getAllUsers(
            @PageableDefault(size = 20, sort = "firstName", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "")
            String search) {
        Page<UserResponseDTO> userResponseDTOPage = userService.getAllUsers(pageable, search);
        return ResponseEntity.ok(HttpResponse.<Page<UserResponseDTO>>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Users found")
                .data(Map.of("users", userResponseDTOPage))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpResponse.<Void>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("User deleted")
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<HttpResponse<UserResponseDTO>> register(@RequestBody @Valid UserCreateDTO dto) {
        UserResponseDTO userResponseDTO = userService.createUser(dto);
        URI location = URI.create("/api/v1/users/" + userResponseDTO.id());
        return ResponseEntity.created(location).body(HttpResponse.<UserResponseDTO>builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("User created")
                .data(Map.of("user", userResponseDTO))
                .timestamp(Instant.now())
                .build()
        );
    }

    @PostMapping("/update-password/{id}")
    public ResponseEntity<HttpResponse<Boolean>> updatePassword(@PathVariable UUID id, @Valid UserEditPasswordDTO dto) {
        boolean isSuccess = userService.updateUserPassword(id, dto);
        return ResponseEntity.ok(HttpResponse.<Boolean>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Password updated")
                .data(Map.of("isSuccess", isSuccess))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @PutMapping("/update-email/{id}")
    public ResponseEntity<HttpResponse<UserResponseDTO>> updateEmail(@PathVariable UUID id, @Valid UserEditEmailDTO dto) {
        UserResponseDTO userResponseDTO = userService.updateUserEmail(id, dto);
        return ResponseEntity.ok(HttpResponse.<UserResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Email updated")
                .data(Map.of("user", userResponseDTO))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse<UserResponseDTO>> updateUser(@PathVariable UUID id, @RequestBody @Valid UserEditDTO dto) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, dto);
        return ResponseEntity.ok(HttpResponse.<UserResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("User updated")
                .data(Map.of("user", userResponseDTO))
                .timestamp(Instant.now())
                .build()
        );
    }
}
