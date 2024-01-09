package com.myfitbody.controllers.impl;

import com.myfitbody.controllers.HttpResponse;
import com.myfitbody.controllers.contracts.AuthController;
import com.myfitbody.domain.user.*;
import com.myfitbody.services.contracts.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<HttpResponse<UserLoginResponseDTO>> login(@RequestBody @Valid UserLoginDTO dto) {
        UserLoginResponseDTO userLoginResponseDTO = authService.loginUser(dto);
        return ResponseEntity.ok(HttpResponse.<UserLoginResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("User logged in")
                .data(Map.of("user", userLoginResponseDTO))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<HttpResponse<UserResponseDTO>> register(@RequestBody @Valid UserCreateDTO dto) {
        UserResponseDTO userResponseDTO = authService.register(dto);
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

    @Override
    @GetMapping("/verify-email/{token}")
    public ResponseEntity<HttpResponse<Boolean>> verifyEmail(@PathVariable UUID token) {
        boolean isSuccess = authService.verifyEmail(token);
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
    @GetMapping("/token-reset-password/{email}")
    public ResponseEntity<HttpResponse<Boolean>> tokenResetPassword(@PathVariable String email) {
        boolean isSuccess = authService.tokenResetPassword(email);
        return ResponseEntity.ok(HttpResponse.<Boolean>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Password reset")
                .data(Map.of("isSuccess", isSuccess))
                .timestamp(Instant.now())
                .build()
        );
    }

    @Override
    @PostMapping("/reset-password/{token}")
    public ResponseEntity<HttpResponse<UserResponseDTO>> resetPassword(@PathVariable UUID token, @RequestBody @Valid UserResetPasswordDTO dto) {
        UserResponseDTO userResponseDTO = authService.resetPassword(token, dto);
        return ResponseEntity.ok(HttpResponse.<UserResponseDTO>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Password reset")
                .data(Map.of("user", userResponseDTO))
                .timestamp(Instant.now())
                .build()
        );
    }
}
