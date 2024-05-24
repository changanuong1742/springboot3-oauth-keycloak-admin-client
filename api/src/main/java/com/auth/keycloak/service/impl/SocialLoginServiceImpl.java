package com.auth.keycloak.service.impl;

import com.auth.keycloak.service.SocialLoginService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class SocialLoginServiceImpl implements SocialLoginService {

    @Value("${url-frontend-guest}")
    private String urlFrontendGuest;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.resourceserver.opaque-token.google.client-secret}")
    private String googleClientSecret;
    @Value("${keycloak.urls.auth}")
    private String authServerUrl;
    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;
    @Override
    public ResponseEntity<?> getUrlAuth() {
        String url = new GoogleAuthorizationCodeRequestUrl(googleClientId,
                urlFrontendGuest,
                Arrays.asList(
                        "email",
                        "profile",
                        "openid")).build();
        return new ResponseEntity<>(url, HttpStatus.OK);
    }



    public String authenticateGoogle(String code) throws IOException {
        return new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(), new GsonFactory(),
                googleClientId,
                googleClientSecret,
                code,
                urlFrontendGuest
        ).execute().getAccessToken();
    }

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> exchangeToken(String token) throws IOException {
        String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
        formData.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("subject_token", token);
        formData.add("subject_issuer", "google");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody();
    }

    @Override
    public ResponseEntity<?> loginWithGoogle(String code) throws IOException {
        String googleToken = this.authenticateGoogle(code);
        return new ResponseEntity<>(this.exchangeToken(googleToken), HttpStatus.OK);
    }

}
