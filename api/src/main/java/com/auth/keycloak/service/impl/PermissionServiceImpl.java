package com.auth.keycloak.service.impl;

import com.auth.keycloak.service.PermissionService;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private final Keycloak keycloak;

    @Override
    public ResponseEntity<?> getAllPermission() {
        Keycloak keycloak = Keycloak.getInstance(
                "http://localhost:8080",
                "master",
                "admin",
                "admin",
                "admin-cli");

        // Xác định realm mà bạn muốn thao tác
        RealmResource realmResource = keycloak.realm("appauth");
        ClientRepresentation client = keycloak.realm("appauth").clients().findByClientId("app-auth-client-id").get(0);

//        // Tìm người dùng cần thêm role ADMIN
        log.info(realmResource.clients().get(client.getId()).roles().list().toString());
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
