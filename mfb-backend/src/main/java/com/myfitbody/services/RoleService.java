package com.myfitbody.services;

import com.myfitbody.domain.exceptions.DatabaseException;
import com.myfitbody.domain.exceptions.ResourceNotFoundException;
import com.myfitbody.domain.role.Role;
import com.myfitbody.domain.role.RoleRequestDTO;
import com.myfitbody.domain.role.RoleResponseDTO;
import com.myfitbody.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(Role::toResponseDTO)
                .toList();
    }


    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleById(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow()
                .toResponseDTO();
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow()
                .toResponseDTO();
    }

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {
        Role role = Role.builder()
                .name(dto.name())
                .build();
        return roleRepository.save(role).toResponseDTO();
    }

    @Transactional
    public RoleResponseDTO updateRole(UUID id, RoleRequestDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow();
        role.setName(dto.name());
        return role.toResponseDTO();
    }

    @Transactional
    public void deleteRole(UUID id) {
        try {
            roleRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID not found:" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
