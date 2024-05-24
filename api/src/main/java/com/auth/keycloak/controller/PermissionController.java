package com.auth.keycloak.controller;

import com.auth.keycloak.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/permissions")
@AllArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PreAuthorize("hasRole('delete permission')")
    @DeleteMapping
    public ResponseEntity<?> permissions() {
        return ResponseEntity.ok("permissionService");
    }

    @GetMapping
    public ResponseEntity<?> getAllPermission(){
        return ResponseEntity.ok(permissionService.getAllPermission());
    }
}
