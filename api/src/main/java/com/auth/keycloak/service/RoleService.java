package com.auth.keycloak.service;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseEntity<?> assignRole(String userId, List<String> role);
    List<RoleRepresentation> fetchAllRoles();
}
