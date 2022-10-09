package com.mauricio.apirestsecurityjwt.dto;

public class JwtAuthResponseDTO {

    private String accessToken;
    private String tokenTipe = "Bearer";    
    
    public JwtAuthResponseDTO(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public JwtAuthResponseDTO(String accessToken, String tokenTipe) {
        this.accessToken = accessToken;
        this.tokenTipe = tokenTipe;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenTipe() {
        return tokenTipe;
    }

    public void setTokenTipe(String tokenTipe) {
        this.tokenTipe = tokenTipe;
    }
}
