package com.auth.keycloak.controller;

import com.auth.keycloak.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
@Slf4j
public class RoleController {
    private final RoleService roleService;

    @PutMapping
    public ResponseEntity<?> assignRole(@RequestBody Map<String, Object> payload) {
        String userId = (String) payload.get("user_id");
        List<String> roleNames = (List<String>) payload.get("role_name");
        return ResponseEntity.ok(roleService.assignRole(userId, roleNames));
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.fetchAllRoles());
    }

}
