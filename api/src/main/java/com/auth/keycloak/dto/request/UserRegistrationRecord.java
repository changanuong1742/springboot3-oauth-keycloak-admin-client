package com.auth.keycloak.dto.request;

public record UserRegistrationRecord(String username, String email,String firstName,String lastName,String password) {
}
