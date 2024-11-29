package com.example.ControleFarmacia.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    public Usuario() {

    }

    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O login não pode estar vazio.")
    @Size(min = 4, max = 20, message = "O login deve ter entre 4 e 20 caracteres.")
    private String username;

    @NotNull(message = "A senha não pode estar vazia.")
    @Size(min = 4, max = 30, message = "A senha deve ter entre 4 e 30 caracteres.")
    private String password;

    @NotNull(message = "A permissão não pode estar vazio.")
    @Size(min = 4, max = 5, message = "A permissão deve ser 'admin' ou 'user'")
    private String role;

    @JsonIgnoreProperties("usuario") // Ignora o carrinho para evitar referência circular
    @OneToOne
    @JoinColumn(name = "carrinho_id", nullable = true)
    private Carrinho carrinho;

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }
    public Carrinho getCarrinho() {
        return this.carrinho;
    }
}
