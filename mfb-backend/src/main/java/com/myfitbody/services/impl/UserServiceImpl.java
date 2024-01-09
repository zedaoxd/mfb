package com.myfitbody.services.impl;

import com.myfitbody.domain.email.EmailBodyType;
import com.myfitbody.domain.exceptions.DatabaseException;
import com.myfitbody.domain.exceptions.ResourceNotFoundException;
import com.myfitbody.domain.role.Role;
import com.myfitbody.domain.role.RoleRequestDTO;
import com.myfitbody.domain.user.*;
import com.myfitbody.repositories.RoleRepository;
import com.myfitbody.repositories.UserRepository;
import com.myfitbody.services.contracts.EmailBodyTypeService;
import com.myfitbody.services.contracts.EmailService;
import com.myfitbody.services.contracts.TokenService;
import com.myfitbody.services.contracts.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailBodyTypeService emailBodyTypeService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable, String search) {
        return userRepository
                .findAllByFirstNameIgnoreCaseStartingWithOrLastNameIgnoreCaseStartingWith(pageable, search, search)
                .map(User::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO getUserById(UUID id) {
        return userRepository
                .findById(id)
                .map(User::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));
    }

    @Transactional
    @Override
    public UserResponseDTO createUser(UserCreateDTO dto) {
        UUID tokenVerifyEmail = UUID.randomUUID();
        String password = passwordEncoder.encode(dto.password());
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        List<Role> userRoles = dto.roles() == null
                ? List.of(role)
                : roleRepository
                .findRolesByNameIn(
                        dto.roles()
                                .stream()
                                .map(RoleRequestDTO::name)
                                .toList()
                );

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(password)
                .tokenVerifyEmail(tokenVerifyEmail)
                .isActive(false)
                .inactiveMessage("User email not verified")
                .roles(userRoles)
                .build();

        var entity = userRepository.save(user);

        String url = "http://localhost:8080/api/v1/users/verify-email/" + tokenVerifyEmail;

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
    public UserResponseDTO updateUser(UUID id, UserEditDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());

        return userRepository
                .save(user)
                .toResponseDTO();
    }

    @Transactional
    @Override
    public boolean updateUserPassword(UUID id, UserEditPasswordDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        userRepository.save(user);

        return true;
    }

    @Transactional
    @Override
    public UserResponseDTO updateUserEmail(UUID id, UserEditEmailDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));

        user.setEmail(dto.email());

        return userRepository
                .save(user)
                .toResponseDTO();
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        try {
            roleRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found:" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
