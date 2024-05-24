package com.auth.keycloak.service;

import com.auth.keycloak.dto.request.LoginRequest;
import com.auth.keycloak.dto.request.UserRegistrationRecord;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface KeycloakUserService {
    UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord);
    void updateUser(String username);
    UserResource getUserResource(String userId);
    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    void forgotPassword(String username);
    ResponseEntity<?> getToken(LoginRequest request);
}
