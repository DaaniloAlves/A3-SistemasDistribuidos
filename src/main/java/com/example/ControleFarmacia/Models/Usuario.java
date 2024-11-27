package com.example.ControleFarmacia.Models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Usuario implements UserDetails {

    public Usuario(String login, String senha) {
        this.username = login;
        this.password = senha;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "O login não pode estar vazio.")
    @Size(min = 4, max = 20, message = "O login deve ter entre 4 e 20 caracteres.")
    private String username;
    @NotNull(message = "A senha não pode estar vazia.")
    @Size(min = 40, max = 100, message = "A senha deve ter entre 40 e 100 caracteres.")
    private String password;
    @NotNull(message = "A permissão não pode estar vazio.")
    @Size(min = 9, max = 10, message = "A permissão deve ser 'role_admin' ou 'role_user'.")
    private String role;

    @NotNull
    @OneToOne
    private Carrinho carrinhos; 

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }
}
