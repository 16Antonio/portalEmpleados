package com.gamez.gestor_turnos.dto;

public class AuthRequest {
    private String dni;
    private String password;

    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}