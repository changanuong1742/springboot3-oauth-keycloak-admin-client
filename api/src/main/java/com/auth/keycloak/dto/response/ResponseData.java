package com.auth.keycloak.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseData {
    private String message;
    private Map<String, Object> errors;
    private Object data;
}
