package com.example.ControleFarmacia.DTOs;

public class CarrinhoDTO {
    private int usuarioId;

    // Construtores, getters e setters
    public CarrinhoDTO(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
