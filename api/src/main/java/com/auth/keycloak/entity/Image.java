package com.auth.keycloak.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "file_name")
    private String filename;
    private String contentType;
    @Column(name = "model_type")
    private String modelType;
    @Column(name = "model_id")
    private String modelId;
}
