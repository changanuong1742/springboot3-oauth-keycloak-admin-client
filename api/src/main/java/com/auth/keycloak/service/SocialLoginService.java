package com.auth.keycloak.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface SocialLoginService {
    ResponseEntity<?> getUrlAuth();
    ResponseEntity<?> loginWithGoogle(String code) throws IOException;
}
