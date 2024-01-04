package com.myfitbody.controllers;

import com.myfitbody.domain.role.RoleRequestDTO;
import com.myfitbody.domain.role.RoleResponseDTO;
import com.myfitbody.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable("id")UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RoleResponseDTO> getRoleByName(@PathVariable("name")String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleRequestDTO dto) {
        return ResponseEntity.ok(roleService.createRole(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable("id")UUID id,
            @RequestBody RoleRequestDTO dto) {
        return ResponseEntity.ok(roleService.updateRole(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id")UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
