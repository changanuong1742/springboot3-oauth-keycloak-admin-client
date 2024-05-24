package com.auth.keycloak.controller;

import com.auth.keycloak.service.KeycloakUserService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final KeycloakUserService keycloakUserService;

    @GetMapping("/{userId}")
    public UserRepresentation getUser(@PathVariable String userId) {
        return keycloakUserService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }

    @PutMapping("/forgot-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.forgotPassword(principal.getName());
    }

    @PutMapping("/update-user")
    public void updateUser(Principal principal) {
        log.info("Updating user: {}", principal.getName());
        keycloakUserService.updateUser(principal.getName());
    }

}
