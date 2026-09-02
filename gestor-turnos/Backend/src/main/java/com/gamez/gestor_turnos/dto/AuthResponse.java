package com.gamez.gestor_turnos.dto;

public class AuthResponse {
    private String token;

    // Constructor
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter y Setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}