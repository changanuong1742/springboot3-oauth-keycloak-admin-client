package com.auth.keycloak.service;

import com.auth.keycloak.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ImageService {

    CompletableFuture<List<Image>> saveUsers(MultipartFile file) throws Exception;

    String saveUserAll(MultipartFile file) throws Exception;
}
