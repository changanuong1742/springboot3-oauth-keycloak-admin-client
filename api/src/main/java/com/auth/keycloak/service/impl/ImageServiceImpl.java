package com.auth.keycloak.service.impl;

import com.auth.keycloak.entity.Image;
import com.auth.keycloak.repository.ImageRepository;
import com.auth.keycloak.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private final ImageRepository imageRepository;

    @Async
    @Override
    public CompletableFuture<List<Image>> saveUsers(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Image> images = parseCSVFile(file);
        logger.info("saving list of users of size {}", images.size(), "" + Thread.currentThread().getName());
        images = imageRepository.saveAll(images);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(images);
    }

    public String saveUserAll(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Image> images = parseCSVFile(file);
        images = imageRepository.saveAll(images);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return "true";
    }

    @Async
    public CompletableFuture<List<Image>> findAllImage() {
        logger.info("get list of user by " + Thread.currentThread().getName());
        List<Image> images = imageRepository.findAll();
        return CompletableFuture.completedFuture(images);
    }

    private List<Image> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Image> images = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Image image = new Image();
                    image.setFilename(data[0]);
                    images.add(image);
                }
                return images;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }

}
