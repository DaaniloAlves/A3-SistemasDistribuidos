package com.example.ControleFarmacia.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario {

    public Usuario() {

    }

    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O login não pode estar vazio.")
    @Size(min = 4, max = 20, message = "O login deve ter entre 4 e 20 caracteres.")
    private String username;

    @NotNull(message = "A senha não pode estar vazia.")
    @Size(min = 5, max = 100, message = "A senha deve ter entre 5 e 100 caracteres.")
    @JsonIgnore // Para não expor a senha no JSON
    private String password;

    @NotNull(message = "A permissão não pode estar vazio.")
    @Size(min = 4, max = 5, message = "A permissão deve ser 'admin' ou 'user'")
    private String role;

    @JsonIgnoreProperties("usuario") // Ignora o carrinho para evitar referência circular
    @OneToOne
    @JoinColumn(name = "carrinho_id", nullable = true)
    private Carrinho carrinho;

    public Usuario(int id, String username, String password, String role) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }
    public void setPassword(String senha) {
        this.password = senha;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Carrinho getCarrinho() {
        return this.carrinho;
    }
    public String getRole() {
        return this.role;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public int getId() {
        return this.id;
    }
}
