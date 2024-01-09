package com.myfitbody.services.impl;

import com.myfitbody.domain.email.EmailBodyType;
import com.myfitbody.domain.exceptions.ResourceNotFoundException;
import com.myfitbody.domain.role.Role;
import com.myfitbody.domain.user.*;
import com.myfitbody.repositories.RoleRepository;
import com.myfitbody.repositories.UserRepository;
import com.myfitbody.services.contracts.AuthService;
import com.myfitbody.services.contracts.EmailBodyTypeService;
import com.myfitbody.services.contracts.EmailService;
import com.myfitbody.services.contracts.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final EmailBodyTypeService emailBodyTypeService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO register(UserCreateDTO dto) {
        UUID tokenVerifyEmail = UUID.randomUUID();
        String password = passwordEncoder.encode(dto.password());
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(password)
                .tokenVerifyEmail(tokenVerifyEmail)
                .isActive(false)
                .inactiveMessage("User email not verified")
                .roles(List.of(role))
                .build();

        var entity = userRepository.save(user);
        String url = "http://localhost:8080/api/v1/auth/verify-email/" + tokenVerifyEmail;
        String body = emailBodyTypeService.formatEmailBody(EmailBodyType.VERIFY_EMAIL, Map.of("[[href]]", url));

        emailService.sendEmail(
                entity.getEmail(),
                "Verifique seu e-mail",
                body
        );

        return entity.toResponseDTO();
    }

    @Transactional
    @Override
    public boolean verifyEmail(UUID token) {
        User user = userRepository
                .findByTokenVerifyEmail(token)
                .orElseThrow(() -> new ResourceNotFoundException("User not found token: " + token));

        user.setTokenVerifyEmail(null);
        user.setInactiveMessage(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }

    @Override
    public UserLoginResponseDTO loginUser(UserLoginDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        return UserLoginResponseDTO.builder()
                .token(token)
                .user(user.toResponseDTO())
                .build();
    }

    @Transactional
    @Override
    public boolean tokenResetPassword(String email) {
        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found email: " + email));

        UUID tokenResetPassword = UUID.randomUUID();

        user.setTokenResetPassword(tokenResetPassword);

        userRepository.save(user);

        String url = "http://localhost:8080/api/v1/auth/reset-password/" + tokenResetPassword;

        String body = emailBodyTypeService.formatEmailBody(EmailBodyType.RESET_PASSWORD, Map.of("[[href]]", url));

        emailService.sendEmail(
                user.getEmail(),
                "Recuperação de senha",
                body
        );

        return true;
    }

    @Transactional
    @Override
    public UserResponseDTO resetPassword(UUID token, UserResetPasswordDTO dto) {
        User user = userRepository
                .findByTokenResetPassword(token)
                .orElseThrow(() -> new ResourceNotFoundException("User not found token: " + token));

        user.setTokenResetPassword(null);
        user.setPassword(passwordEncoder.encode(dto.password()));

        userRepository.save(user);

        return user.toResponseDTO();
    }
}
