package com.auth.keycloak.controller;

import com.auth.keycloak.dto.request.LoginRequest;
import com.auth.keycloak.dto.request.UserRegistrationRecord;
import com.auth.keycloak.service.KeycloakUserService;
import com.auth.keycloak.service.SocialLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final KeycloakUserService keycloakUserService;
    private final SocialLoginService socialLoginService;

    @PostMapping
    public UserRegistrationRecord createUser(@RequestBody UserRegistrationRecord userRegistrationRecord) {
        return keycloakUserService.createUser(userRegistrationRecord);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            ResponseEntity<?> tokenResponse = keycloakUserService.getToken(request);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized: " + e.getMessage());
        }
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> payload) throws IOException {
        try {
            String code = payload.get("code");
            return new ResponseEntity<>(socialLoginService.loginWithGoogle(code), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Unauthorized: " + e.getMessage());
        }
    }

    @GetMapping("/google/get-url-auth")
    public ResponseEntity<?> getUrlAuth() {
        return socialLoginService.getUrlAuth();
    }

}
