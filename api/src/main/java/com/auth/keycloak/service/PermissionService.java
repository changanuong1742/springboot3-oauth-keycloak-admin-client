package com.auth.keycloak.service;

import org.springframework.http.ResponseEntity;

public interface PermissionService {
    ResponseEntity<?> getAllPermission();
}
