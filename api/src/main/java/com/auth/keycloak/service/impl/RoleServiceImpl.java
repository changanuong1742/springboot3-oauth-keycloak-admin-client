package com.auth.keycloak.service.impl;

import com.auth.keycloak.dto.response.ResponseData;
import com.auth.keycloak.service.KeycloakUserService;
import com.auth.keycloak.service.RoleService;
import jakarta.ws.rs.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@
        RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.urls.auth}")
    private String authServerUrl;

    @Value("${keycloak.clientId}")
    private String clientId;

    private final Keycloak keycloak;

    private final KeycloakUserService keycloakUserService;

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }

    @Override
    public ResponseEntity<?> assignRole(String username, List<String> roleNames) {
        ResponseData responseData = new ResponseData();

        Keycloak keycloak = Keycloak.getInstance(
                authServerUrl,
                "master",
                "admin",
                "admin",
                "admin-cli");

        // Xác định realm mà bạn muốn thao tác
        RealmResource realmResource = keycloak.realm(realm);

//        // Tìm người dùng cần thêm role ADMIN
        UserRepresentation user = realmResource.users().searchByUsername(username, true).get(0);

        List<RoleRepresentation> userRoles = realmResource.users().get(user.getId()).roles().realmLevel().listAll();

        // Tạo danh sách các role cần xóa
        List<RoleRepresentation> rolesToRemove = new ArrayList<>(userRoles);

        // Thêm role cho người dùng
        for (String roleName : roleNames) {
            RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
            realmResource.users().get(user.getId()).roles().realmLevel().add(Arrays.asList(role));
            // Nếu role đã tồn tại trong danh sách, loại bỏ nó khỏi danh sách cần xóa
            rolesToRemove.removeIf(r -> r.getName().equals(roleName));
        }

        // Xóa các role không được truyền vào
        for (RoleRepresentation roleToRemove : rolesToRemove) {
            realmResource.users().get(user.getId()).roles().realmLevel().remove(Arrays.asList(roleToRemove));
        }

        responseData.setMessage("Update role successfully");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @Override
    public List<RoleRepresentation> fetchAllRoles() {
        RolesResource rolesResource = getRolesResource();
        List<RoleRepresentation> roles = rolesResource.list();
        log.info("Fetched all roles: {}", roles);
        return roles;
    }
}
