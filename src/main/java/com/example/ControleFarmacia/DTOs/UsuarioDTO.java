package com.example.ControleFarmacia.DTOs;

public class UsuarioDTO {
    private String username;
    private String password;
    private String role;

    public UsuarioDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public UsuarioDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

