package com.example.ControleFarmacia.Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O login não pode estar vazio.")
    @Size(min = 4, max = 20, message = "O login deve ter entre 4 e 20 caracteres.")
    private String login;
    @NotNull(message = "A senha não pode estar vazia.")
    @Size(min = 5, max = 30, message = "A senha deve ter entre 5 e 30 caracteres.")
    private String senha;

    @NotNull
    @OneToOne
    private Carrinho carrinhos; 
}
